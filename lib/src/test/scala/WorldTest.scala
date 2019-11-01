import java.awt.{ Graphics, Graphics2D, Paint }

import scala.concurrent.duration.FiniteDuration

import com.sksamuel.scrimage.Color
import lib.World
import lib.World.{ Button, Image, Point, Rectangle, Scene }

object WorldTest extends App {

  case class State(width: Int, height: Int, boxX: Int, boxY: Int)

  val flower = Image.fromResource("/image/flower.png")
  val flowerBuf = flower.underlying

  def draw(state: State): Scene = {
    val rectangle = Rectangle(state.boxX, state.boxY, 100, 100, solid = true, color = Color(255, 0, 0))
    Scene(
      drawables = Seq(flower, rectangle),
      texts = Seq()
    )
  }

  def onTick(state: State): State = {
    val boxX = state.boxX
    val newX = if (boxX + 100 >= state.width) {
      0
    } else {
      boxX + 1
    }
    val boxY = state.boxY
    val newY = if (boxY + 100 >= state.height) {
      0
    } else {
      boxY + 1
    }
    state.copy(boxX = newX, boxY = newY)
  }

  def onMouse(state: State, point: Point, mouse: Button): State = {
    println(s"Mouse ${mouse} ${point}")
    state
  }

  val world = World[State](
    initial = State(500, 300, 0, 0),
    onTick = onTick,
    onMouse = onMouse,
    draw = draw
  )

}
