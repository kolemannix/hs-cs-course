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

  val PADDLE_SPEED = 5

  val PADDLE_WIDTH = 10
  val PADDLE_HEIGHT = 70

  val middle = Point(WIDTH / 2, HEIGHT / 2)

  case class PongState(
    ballPosition: Point,
    paddle1Y: Int,
    paddle2Y: Int,
    score1: Int,
    score2: Int,
    ballSpeedX: Int,
    ballSpeedY: Int,
    isPaused: Boolean
  ) {
    def isPaddle1Collision(): Boolean = {
      ballPosition.x <= (PADDLE_1_X + PADDLE_WIDTH) &&
        ballPosition.y < (paddle1Y + PADDLE_HEIGHT) &&
        ballPosition.y > (paddle1Y)
    }

    def isPaddle2Collision(): Boolean = {
      (ballPosition.x + BALL_SIZE) >= PADDLE_2_X &&
        ballPosition.y < (paddle2Y + PADDLE_HEIGHT) &&
        ballPosition.y > (paddle2Y)
    }

    def isScore2(): Boolean = {
      ballPosition.x <= 0
    }

    def isScore1(): Boolean = {
      ballPosition.x + BALL_SIZE >= WIDTH
    }

    def paddle1Down(): PongState = {
      copy(paddle1Y = paddle1Y + PADDLE_SPEED)
    }
    def paddle1Up(): PongState = {
      copy(paddle1Y = paddle1Y - PADDLE_SPEED)
    }
    def paddle2Down(): PongState = {
      copy(paddle2Y = paddle2Y + PADDLE_SPEED)
    }
    def paddle2Up(): PongState = {
      copy(paddle2Y = paddle2Y - PADDLE_SPEED)
    }

    def togglePaused(): PongState = {
      copy(isPaused = !isPaused)
    }
  }

  val background = Rectangle(0, 0, WIDTH, HEIGHT, solid = true, color = World.black)

  val initialState = PongState(
    ballPosition = Point(middle.x + 1, middle.y),
    paddle1Y = middle.y,
    paddle2Y = middle.y,
    score1 = 0,
    score2 = 5,
    ballSpeedX = if (Random.nextBoolean()) 5 else -5,
    ballSpeedY = 0,
    isPaused = true
  )

  def onTick(state: PongState): PongState = {
    if (state.isPaused) state else {
      if (state.isScore1()) {
        state.copy(
          ballSpeedX = 1,
          isPaused = true,
          score1 = state.score1 + 1
        )
      } else if (state.isScore2()) {
        state.copy(
          ballSpeedX = -1,
          isPaused = true,
          score2 = state.score2 + 1
        )
      } else {
        // Normal update
        val p1 = state.isPaddle1Collision()
        val p2 = state.isPaddle2Collision()
        val newSpeedX = if (p1 || p2) {
          state.ballSpeedX * -1
        } else state.ballSpeedX

        val newBallX = state.ballPosition.x + newSpeedX
        val newBallY = state.ballPosition.y + state.ballSpeedY
        state.copy(
          ballSpeedX = newSpeedX,
          ballPosition = Point(newBallX, newBallY)
        )
      }
    }
  }

  def onKey(state: PongState, key: Key): PongState = {
    key match {
      case Letter('w') => state.paddle1Up()
      case Letter('s') => state.paddle1Down()
      case UpArrow => state.paddle2Up()
      case DownArrow => state.paddle2Down()
      case Space => state.togglePaused()
      case _ => state
    }

  }

  def onKeyOld(state: PongState, key: Key): PongState = {
    val newPaddle1 = if (key == Letter('w')) {
      state.paddle1Y - PADDLE_SPEED
    } else if (key == Letter('s')) {
      state.paddle1Y + PADDLE_SPEED
    } else state.paddle1Y
    val newPaddle2 = if (key == UpArrow) {
      state.paddle2Y - PADDLE_SPEED
    } else if (key == DownArrow) {
      state.paddle2Y + PADDLE_SPEED
    } else state.paddle2Y
    val newIsPaused = if (key == Space) !state.isPaused else state.isPaused
    state.copy(
      paddle1Y = newPaddle1,
      paddle2Y = newPaddle2,
      isPaused = newIsPaused
    )
  }

  def draw(state: PongState): Scene = {
    val ball = Ellipse(state.ballPosition.x, state.ballPosition.y, BALL_SIZE, BALL_SIZE, true, World.white)
    val statePrint = Text(50, HEIGHT - 30, state.toString, 24, World.white)

    val paddle1 = Rectangle(PADDLE_1_X, state.paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT, color = World.white)
    val paddle2 = Rectangle(PADDLE_2_X, state.paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT, color = World.white)

    val scoreString = state.score1.toString + " - " + state.score2.toString
    val scoreText = Text(middle.x - 30, 20, scoreString, 24, World.white)
    val pausedPrint = Text(middle.x - 40, 50, if (state.isPaused) "PAUSE" else "", 24, World.white)

    Scene(
      Seq(background, statePrint, ball, paddle1, paddle2, pausedPrint, scoreText)
    )
  }

  World(
    name = "Pong",
    width = WIDTH,
    height = HEIGHT,
    //    tickInterval = FiniteDuration(100, "ms"),
    initial = initialState,
    onTick = onTick,
    onKey = onKey,
    draw = draw
  )

}
