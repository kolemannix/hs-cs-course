package solutions

import com.sksamuel.scrimage.Pixel
import lib.World
import lib.World.{ Button, Ellipse, Image, Point, Rectangle, Scene, Text }

object ExampleWorld extends App {

  case class State(
    isGray: Boolean,
    drawOval: Boolean,
    ticker: Int
  ) {
    def printOut: String = {
      "Gray? " + isGray + "\n" +
      "drawOval? " + drawOval + "\n" +
      "ticker " + ticker
    }
  }

  val flower = Image.fromResource("/image/flower.png", Point.origin, 600, 400)
  val grayFlower = flower.map({ p =>
    val avg = (p.red + p.green + p.blue) / 3
    Pixel(avg, avg, avg, p.alpha)
  })

  val cyanOval = Ellipse(100, 100, 100, 200, true, World.cyan)

  def empty = State(false, true, 0)

  def draw(state: State): Scene = {
    val currentFlower = if (state.isGray) grayFlower else flower
    val currentOval = if (state.drawOval) Some(cyanOval) else None
    val helperText = Text(30, 50, state.printOut, 24, World.white)
    val currentBox = Rectangle(500, state.ticker % 350, 50, 50, false, World.red)
    val drawables = Seq(Some(currentFlower), currentOval, Some(helperText), Some(currentBox)).flatten
    Scene(drawables)
  }

  def onTick(state: State): State = {
    if (state.ticker == 500) {
      println("Ticker is 500, toggle oval and reset ticker")
      state.copy(
        ticker = 0,
        drawOval = !state.drawOval
      )
    } else {
      val newTicker = state.ticker + 1
      state.copy(ticker = newTicker)
    }

  }

  def onMouse(state: State, point: Point, mouse: Button): State = {
    val isGray = state.isGray
    val updated = !isGray
    println(s"Switching isGray from ${isGray} to ${updated}")
    state.copy(isGray = updated)
  }

  val world = World[State](
    initial = empty,
    onTick = onTick,
    onMouse = onMouse,
    draw = draw
  )

}
