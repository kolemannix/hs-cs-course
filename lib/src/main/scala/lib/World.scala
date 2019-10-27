package lib

import java.awt.Graphics
import java.awt.image.BufferedImage

import javax.swing.{ JPanel, WindowConstants }
import lib.World.{ Button, Key, Scene }

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

  case class Scene(
    images: Seq[BufferedImage],
    texts: Seq[String]
  )

  def apply[State](
    initial: State,
    onTick: (State => State),
    onKey: (State, Key) => State,
    onMouse: (State, Double, Double, Button) => State,
    stopWhen: (State) => Boolean,
    draw: (State => Scene)
  ): World[State] = {
    new World[State](initial, onTick, onKey, onMouse, stopWhen, draw)
  }

}

class World[State](
  initial: State,
  val onTick: (State => State),
  val onKey: (State, Key) => State,
  val onMouse: (State, Double, Double, Button) => State,
  val stopWhen: (State) => Boolean,
  val draw: (State => Scene)
) {

  var state = initial

  def makePanel(render: (Graphics, State) => Unit) = {
    val panel = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        render(g, state)
      }
    }
    panel
  }

  def init(title: String = "World"): Unit = {
    val swingFrame = new javax.swing.JFrame(title)

    val render: (Graphics, State) => Unit = {
      case (g: Graphics, st: State) =>
        println("Rendering")
        val scene = draw(state)
        scene.images.map { image =>
          g.drawImage(image, 0, 0, null)
        }
    }
    val panel = makePanel(render)

    swingFrame.add(panel)

    swingFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
    swingFrame.setSize(400, 600)
    swingFrame.setVisible(true)
  }
  // New JFrame, render loop, calling tick and onKey and stuff
}
