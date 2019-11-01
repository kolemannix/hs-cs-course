package lib

import java.awt.event.{ MouseEvent, MouseListener }
import java.awt.image.BufferedImage
import java.awt.{ Graphics, Graphics2D }
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

import scala.concurrent.duration.FiniteDuration

import com.sksamuel.scrimage.Color
import javax.swing.{ JFrame, JPanel, WindowConstants }
import lib.World._

object World {

  sealed trait Key

  case class Letter(l: String) extends Key {
    require(l.size == 1 && l.head.isLetterOrDigit)
  }
  case class Special(code: String) extends Key {
    /*
   Backspace key: "backspace"

 Tab key: "tab"

 Enter key: "enter"

 Shift key: "shift"

 Control key: "control"

 Pause key: "pause"

 Escape key: "escape"

 Prior key: "prior"

 Next key: "next"

 End key: "end"

 Home key: "home"

 Left arrow: "left"

 Up arrow: "up"

 Right arrow: "right"

 Down arrow: "down"

 Print key: "print"

 Insert key: "insert"

 Delete key: "delete"

 Backspace key: "backspace"

 Num lock key: "numlock"

 Scroll key: "scroll"
    */
  }

  sealed trait Button
  case object LeftButton extends Button
  case object RightButton extends Button

  sealed trait Drawable

  case class Point(x: Int, y: Int)
  object Point {
    def origin: Point = Point(0, 0)
  }
  case class Image(underlying: BufferedImage, start: Point) extends Drawable {
    // TODO: Delegates to underlying w/h, make it scalable
    def width: Int = underlying.getWidth
    def height: Int = underlying.getHeight
  }
  object Image {
    def fromResource(fileName: String): Image = {
      val buf = com.sksamuel.scrimage.Image.fromResource(fileName).toNewBufferedImage()
      Image(buf, Point.origin)
    }
  }

  case class Rectangle(
    x: Int, y: Int, width: Int, height: Int,
    solid: Boolean = true, color: Color = Color.Black
  ) extends Drawable

  case class Scene(
    drawables: Seq[Drawable],
    texts: Seq[String]
  )

  def simple[S](
    initial: S,
    onTick: (S => S),
    draw: Draw[S]
  ): World[S] = {
    new World[S](initial, defaultTickInterval, onTick, defaultOnKey, defaultOnMouse, defaultStopWhen, draw)
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
    initial: S,
    tickInterval: FiniteDuration = defaultTickInterval,
    onTick: OnTick[S],
    onKey: OnKey[S] = defaultOnKey[S],
    onMouse: OnMouse[S] = defaultOnMouse[S],
    stopWhen: StopWhen[S] = defaultStopWhen[S],
    draw: (S => Scene)
  ): World[S] = {
    new World[S](initial, tickInterval, onTick, onKey, onMouse, stopWhen, draw)
  }

}

class World[S](
  initial: S,
  tickInterval: FiniteDuration,
  val onTick: OnTick[S],
  val onKey: OnKey[S],
  val onMouse: OnMouse[S],
  val stopWhen: StopWhen[S],
  val draw: (S => Scene)
) {

  val state = new AtomicReference(initial)

  var deadline = tickInterval.fromNow

  var myFrame = init()

  def stop(): Unit = {
    myFrame.setVisible(false)
  }

  def makePanel(render: (Graphics2D, S) => Unit) = {
    val panel = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        render(g.asInstanceOf[Graphics2D], state)
      }
    }
    panel.addMouseListener(new MouseListener {
      override def mouseClicked(e: MouseEvent): Unit = {
        state.set(onMouse(state, Point(e.getX, e.getY), LeftButton))
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
    panel
  }

  def render: (Graphics2D, S) => Unit = {
    case (g, st) =>
      val scene = draw(state)
      scene.drawables.map {
        case Image(underlying, start) =>
          g.drawImage(underlying, start.x, start.y, null)
        case Rectangle(x, y, width, height, solid, color) =>
          withColor(g, color.toAWT) {
            if (solid) {
              g.fillRect(x, y, width, height)
            } else {
              g.drawRect(x, y, width, height)
            }
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
    swingFrame.setSize(400, 600)
    swingFrame.setVisible(true)
    swingFrame
  }
  // New JFrame, render loop, calling tick and onKey and stuff

  while (true) {
    if (deadline.isOverdue()) {
      if (stopWhen(state)) {
        stop()
      }
      state.set(onTick(state))
      deadline = tickInterval.fromNow
      myFrame.repaint()
    }
  }
}
