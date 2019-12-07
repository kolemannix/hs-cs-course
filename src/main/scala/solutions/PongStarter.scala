package solutions

import scala.concurrent.duration.FiniteDuration
import scala.util.Random

import com.sksamuel.scrimage.Image
import lib.World
import lib.World._

object PongStarter extends App {

  val WIDTH = 800
  val HEIGHT = 400

  val BALL_SIZE = 30

  val PADDLE_1_X = 50
  val PADDLE_2_X = WIDTH - 50

  val middle = Point(WIDTH / 2, HEIGHT / 2)

  case class PongState(
    ballPosition: Point,
    paddle1Y: Int,
    paddle2Y: Int,
    score1: Int,
    score2: Int,
    ballSpeedX: Int,
    ballSpeedY: Int
  )

  val background = Rectangle(0, 0, WIDTH, HEIGHT, solid = true, color = World.black)

  val initialState = PongState(
    ballPosition = middle,
    paddle1Y = middle.y,
    paddle2Y = middle.y,
    score1 = 0,
    score2 = 5,
    ballSpeedX = if (Random.nextBoolean()) 1 else -1,
    ballSpeedY = 1
  )

  def onTick(state: PongState): PongState = {
    val newBallX = state.ballPosition.x + state.ballSpeedX
    val newBallY = state.ballPosition.y + state.ballSpeedY
    state.copy(ballPosition = Point(newBallX, newBallY))
  }

  def onKey(state: PongState, key: Key): PongState = {
    val newPaddle1 = if (key == Letter('w')) {
      state.paddle1Y - 7
    } else if (key == Letter('s')) {
      state.paddle1Y + 7
    } else state.paddle1Y
    state.copy(paddle1Y = newPaddle1)
  }

  def draw(state: PongState): Scene = {
    val ball = Ellipse(state.ballPosition.x, state.ballPosition.y, BALL_SIZE, BALL_SIZE, true, World.white)
    val statePrint = Text(50, 40, state.toString, 24, World.white)
    val paddle1 = Rectangle(PADDLE_1_X, state.paddle1Y, 10, 50, color = World.white)
    val paddle2 = Rectangle(PADDLE_2_X, state.paddle2Y, 10, 50, color = World.white)
    Scene(
      Seq(background, statePrint, ball, paddle1, paddle2)
    )
  }

  World(
    name = "Pong",
    width = WIDTH,
    height = HEIGHT,
//    tickInterval = FiniteDuration(500, "ms"),
    initial = initialState,
    onTick = onTick,
    onKey = onKey,
    draw = draw
  )

}
