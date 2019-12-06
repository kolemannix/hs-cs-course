package solutions

import scala.concurrent.duration.FiniteDuration

import com.sksamuel.scrimage.Image
import lib.World
import lib.World._

object PongStarter extends App {

  val WIDTH = 800
  val HEIGHT = 400

  val BALL_SIZE = 30

  val center = Point(WIDTH / 2, HEIGHT / 2)

  case class PongState(
    ballPosition: Point,
    ballDirectionX: Boolean,
    ballDirectionY: Boolean
  )

  val background = Rectangle(0, 0, WIDTH, HEIGHT, solid = true, color = World.black)

  val initialState = PongState(
    ballPosition = center,
    ballDirectionX = true,
    ballDirectionY = true
  )

  def onTick(state: PongState): PongState = {
    val newBallX = if (state.ballDirectionX) {
      state.ballPosition.x + 1
    } else {
      state.ballPosition.x - 1
    }
    val newBallY = if (state.ballDirectionY) {
      state.ballPosition.y + 1
    } else {
      state.ballPosition.y - 1
    }
    state.copy(ballPosition = Point(newBallX, newBallY))
  }

  def draw(state: PongState): Scene = {
    val ball = Ellipse(state.ballPosition.x, state.ballPosition.y, BALL_SIZE, BALL_SIZE, true, World.white)
    val statePrint = Text(50, 40, state.toString, 24, World.white)
    Scene(
      Seq(background, statePrint, ball)
    )
  }

  World(
    name = "Pong",
    width = WIDTH,
    height = HEIGHT,
    tickInterval = FiniteDuration(500, "ms"),
    initial = initialState,
    onTick = onTick,
    draw = draw
  )

}
