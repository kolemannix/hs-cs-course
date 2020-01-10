package lib

import java.awt.event.{ KeyEvent, KeyListener, MouseEvent, MouseListener }
import java.awt.image.BufferedImage
import java.awt.{ AlphaComposite, Font, Graphics, Graphics2D }
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

import scala.concurrent.duration.FiniteDuration

import com.sksamuel.scrimage.{ Color, Pixel, Image => Scrimage }
import javax.swing.{ JFrame, JPanel, WindowConstants }
import lib.World._

object World {

  final val black: Color = Color.Black
  final val white: Color = Color.White
  final val red: Color = Color.awt2color(java.awt.Color.red)
  final val blue: Color = Color.awt2color(java.awt.Color.blue)
  final val green: Color = Color.awt2color(java.awt.Color.green)
  final val yellow: Color = Color.awt2color(java.awt.Color.yellow)
  final val cyan: Color = Color.awt2color(java.awt.Color.cyan)

  sealed trait Key

  case class Letter(l: Char) extends Key
  case object LeftArrow extends Key
  case object RightArrow extends Key
  case object UpArrow extends Key
  case object DownArrow extends Key
  case object Space extends Key
  case object Backspace extends Key

  case class BoundingBox(x1: Int, y1: Int, x2: Int, y2: Int) {
    def contains(point: Point): Boolean = {
      point.x >= x1 &&
        point.y >= y1 &&
        point.x <= x2 &&
        point.y <= y2
    }
  }

  object BoundingBox {
    def fromRect(x: Int, y: Int, width: Int, height: Int) = BoundingBox(x, y, x + width, y + height)
  }

  sealed trait Button
  case object LeftButton extends Button
  case object RightButton extends Button

  sealed trait Drawable {
    def hitbox: BoundingBox
  }

  case class Point(x: Int, y: Int)
  object Point {
    def origin: Point = Point(0, 0)
  }
  case class Sprite(
    underlying: Scrimage,
    start: Point,
  ) extends Drawable {
    lazy val bufImg: BufferedImage = underlying.toNewBufferedImage(BufferedImage.TYPE_INT_ARGB)
    def width: Int = underlying.width
    def height: Int = underlying.height

    def map(fn: Pixel => Pixel): Sprite = {
      new Sprite(lib.Img.transformImage(underlying, fn), start)
    }

    override def hitbox: BoundingBox = {
      BoundingBox.fromRect(start.x, start.y, width, height)
    }
  }
  object Sprite {
    def apply(image: Scrimage, start: Point = Point.origin, width: Int = -1, height: Int = -1): Sprite = {
      val img = if (width != -1 && height != -1) {
        image.scaleTo(width, height)
      } else {
        image
      }
      new Sprite(img, start)
    }
    def fromResource(
      fileName: String,
      start: Point = Point.origin,
      width: Int = -1,
      height: Int = -1
    ): Sprite = {
      val image = Scrimage.fromResource(fileName)
      apply(image, start, width, height)
    }
  }

  case class Rectangle(
    x: Int, y: Int, width: Int, height: Int,
    solid: Boolean = true, color: Color = Color.Black
  ) extends Drawable {
    override def hitbox: BoundingBox = BoundingBox.fromRect(x, y, width, height)
  }

  case class Ellipse(
    x: Int, y: Int, width: Int, height: Int,
    solid: Boolean = true, color: Color = Color.Black
  ) extends Drawable {
    override def hitbox: BoundingBox = BoundingBox.fromRect(x, y, width, height)
  }

  case class Text(
    x: Int, y: Int,
    text: String, size: Int, color: Color = Color.Black
  ) extends Drawable {
    lazy val font: Font = new Font("Arial", Font.BOLD, size)
    override def hitbox: BoundingBox = BoundingBox.fromRect(x, y, 0, 0)
  }

  //  case class Line(
  //    x: Int, y: Int, weight: Int, color: Color = Color.Black
  //  ) extends Drawable

  case class Scene(
    drawables: Seq[Drawable]
  )

  def simple[S](
    initial: S,
    onTick: (S => S),
    draw: Draw[S]
  ): World[S] = {
    new World[S]("World", initial, defaultTickInterval, 600, 400, onTick, defaultOnKey, defaultOnMouse, defaultStopWhen, draw)
  }

  type OnKey[S] = (S, Key) => S
  type OnMouse[S] = (S, Point, Button) => S
  type OnTick[S] = (S) => S
  type StopWhen[S] = (S) => Boolean
  type Draw[S] = (S) => Scene

  private def defaultOnKey[S]: OnKey[S] = { (s: S, _: Key) => s }
  private def defaultOnMouse[S]: OnMouse[S] = { (s: S, _: Point, _: Button) => s }
  private def defaultStopWhen[S]: StopWhen[S] = { (_: S) => false }
  private val defaultTickInterval = FiniteDuration(25, TimeUnit.MILLISECONDS)

  def apply[S](
    name: String,
    initial: S,
    tickInterval: FiniteDuration = defaultTickInterval,
    width: Int = 600,
    height: Int = 400,
    onTick: OnTick[S],
    onKey: OnKey[S] = defaultOnKey[S],
    onMouse: OnMouse[S] = defaultOnMouse[S],
    stopWhen: StopWhen[S] = defaultStopWhen[S],
    draw: (S => Scene)
  ): World[S] = {
    new World[S](name, initial, tickInterval, width, height, onTick, onKey, onMouse, stopWhen, draw)
  }

}

class World[S](
  name: String,
  initial: S,
  tickInterval: FiniteDuration,
  width: Int,
  height: Int,
  val onTick: OnTick[S],
  val onKey: OnKey[S],
  val onMouse: OnMouse[S],
  val stopWhen: StopWhen[S],
  val draw: (S => Scene)
) {

  // TODO: Atomicity
  // val state = new AtomicReference(initial)
  private var state = initial

  private var deadline = tickInterval.fromNow

  private var myFrame = init(name)

  def stop(): Unit = {
    myFrame.setVisible(false)
  }

  private def makePanel(render: (Graphics2D, S) => Unit) = {
    val panel = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        render(g.asInstanceOf[Graphics2D], state)
      }
      addKeyListener(new KeyListener {
        override def keyTyped(e: KeyEvent): Unit = { }
        override def keyReleased(e: KeyEvent): Unit = { }
        override def keyPressed(e: KeyEvent): Unit = {
          val key = e.getKeyCode match {
            case KeyEvent.VK_LEFT => LeftArrow
            case KeyEvent.VK_RIGHT => RightArrow
            case KeyEvent.VK_UP => UpArrow
            case KeyEvent.VK_DOWN => DownArrow
            case KeyEvent.VK_SPACE => Space
            case KeyEvent.VK_BACK_SPACE => Backspace
            case _ if e.getKeyChar.isValidChar => Letter(e.getKeyChar)
          }
          state = onKey(state, key)
        }
      }
      )
    }

    panel.addMouseListener(new MouseListener {
      override def mouseClicked(e: MouseEvent): Unit = {
        val button = e.getButton match {
          case MouseEvent.BUTTON1 => LeftButton
          case MouseEvent.BUTTON2 => RightButton
          case _ => LeftButton
        }
        state = onMouse(state, Point(e.getX, e.getY), button)
      }
      override def mousePressed(e: MouseEvent): Unit = {

      }
      override def mouseReleased(e: MouseEvent): Unit = {

      }
      override def mouseEntered(e: MouseEvent): Unit = {

      }
      override def mouseExited(e: MouseEvent): Unit = {

      }
    })
    panel.setFocusable(true)
    panel
  }

  def render: (Graphics2D, S) => Unit = {
    case (g, st) =>
      val scene = draw(state)
      scene.drawables.map {
        case img: Sprite =>
          g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f))
          g.drawImage(img.bufImg, img.start.x, img.start.y, null, null)
        case Ellipse(x, y, width, height, solid, color) =>
          withColor(g, color.toAWT) {
            if (solid) g.fillOval(x, y, width, height)
            else g.drawOval(x, y, width, height)
          }
        //        case Line(x, y, weight, color) =>
        //          withColor(g, color.toAWT) {
        //            g.drawLine(x1, y1, x2, y2)
        //          }
        case Rectangle(x, y, width, height, solid, color) =>
          withColor(g, color.toAWT) {
            if (solid) {
              g.fillRect(x, y, width, height)
            } else {
              g.drawRect(x, y, width, height)
            }
          }
        case t@Text(x, y, text, size, color) =>
          withColor(g, color.toAWT) {
            g.setFont(t.font)
            g.drawString(text, x, y)
          }
      }
      ()
  }

  def withColor(g2: Graphics2D, color: java.awt.Color)(value: => Unit): Unit = {
    val oldColor = g2.getColor
    g2.setColor(color)
    value
    g2.setColor(oldColor)
  }

  def init(title: String = "World"): JFrame = {
    val swingFrame = new javax.swing.JFrame(title)

    val panel = makePanel(render)

    swingFrame.add(panel)

    swingFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
    swingFrame.setSize(width, height + 20)
    swingFrame.setVisible(true)
    swingFrame
  }
  // New JFrame, render loop, calling tick and onKey and stuff

  while (true) {
    if (deadline.isOverdue()) {
      if (stopWhen(state)) {
        stop()
      }
      state = onTick(state)
      deadline = tickInterval.fromNow
      myFrame.repaint()
    }
  }
}
