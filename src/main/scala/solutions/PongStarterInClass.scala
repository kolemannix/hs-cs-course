package solutions

import scala.util.Random

import lib.World._
import lib.World


object PongStarterInClass extends App {

  val WIDTH = 600
  val HEIGHT = 300

  val middle = Point(WIDTH / 2, HEIGHT / 2)

  case class PongState(
    ballPoint: Point,
    paddle1Y: Int,
    paddle2Y: Int,
    score1: Int,
    score2: Int,
    ballSpeedX: Int,
    ballSpeedY: Int
  )

  val gameStart = PongState(
    ballPoint = middle,
    paddle1Y = middle.y,
    paddle2Y = middle.y,
    score1 = 0,
    score2 = 5,
    ballSpeedX = if (Random.nextBoolean()) 1 else -1,
    ballSpeedY = 1
  )

  def onTick(old: PongState): PongState = {
    old
  }

  val background = Rectangle(0, 0, WIDTH, HEIGHT, color = World.black)
  val BALL_SIZE = 30

  def draw(state: PongState): Scene = {
    val ball = Ellipse(
      state.ballPoint.x, state.ballPoint.y, BALL_SIZE, BALL_SIZE, color = World.white
    )
    Scene(Seq(background, ball))
  }

  World(
    name = "Pong",
    initial = gameStart,
    width = WIDTH,
    height = HEIGHT,
    onTick = onTick,
    draw = draw
  )

}
