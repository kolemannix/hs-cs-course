package solutions

import scala.concurrent.duration.FiniteDuration
import scala.util.Random

import com.sksamuel.scrimage.Color
import lib.World
import lib.World._

object EatForever extends App {

  case class GameState(score: Int, player: Point, food: Seq[Point]) {
    def moveRight(): GameState = copy(player = Point(player.x + 1, player.y))
    def moveLeft(): GameState = copy(player = Point(player.x - 1, player.y))
    def moveUp(): GameState = copy(player = Point(player.x, player.y - 1))
    def moveDown(): GameState = copy(player = Point(player.x, player.y + 1))
  }
  val WIDTH = 600
  val HEIGHT = 600
  val center = Point(WIDTH / 2, HEIGHT / 2)

  val FOOD_RADIUS = 2

  val initialState = GameState(5, center, Nil)

  def onTick(state: GameState): GameState = {
    val newFood = if (Random.nextInt(12) == 1) Some(Point(Random.nextInt(WIDTH), Random.nextInt(HEIGHT))) else None
    val (eaten, notEaten) = state.food.partition { foodPosition =>
      circlesIntersect(state.player, state.score / 2, foodPosition, FOOD_RADIUS)
      // pointInsideCircle(state.player, state.score / 2, food)
    }
    val newScore = state.score + eaten.size
    state.copy(food = notEaten ++ newFood.toSeq, score = newScore)
  }

  val background = Rectangle(0, 0, WIDTH, HEIGHT, color = World.white)

  def draw(state: GameState): Scene = {
    val foods = state.food.map(food => Ellipse(food.x, food.y, FOOD_RADIUS * 2, FOOD_RADIUS * 2, color = World.black))
    val player = Ellipse(state.player.x, state.player.y, state.score, state.score, color = World.red)
    Scene(
      Seq(background, player) ++ foods
    )
  }

  def onKey(state: GameState, key: Key): GameState = {
    key match {
      case RightArrow => state.moveRight()
      case LeftArrow => state.moveLeft()
      case UpArrow => state.moveUp()
      case DownArrow => state.moveDown()
      case _ => state
    }

  }

  World(
    name = "EatForever",
    width = WIDTH,
    height = HEIGHT,
    tickInterval = FiniteDuration(25, "ms"),
    initial = initialState,
    onTick = onTick,
    onKey = onKey,
    draw = draw
  )


  /* Ideas */

  // Idea: Change the width and height of the game; make it a corridor

  // Idea: Print the score in top-center of the game

  // Idea: Faster player speed

  // Idea: Food of random size and value and color
  case class Food(point: Point, size: Int, color: Color)

  // Idea: Food decays over time and disappears

  // Idea: Add green poison that shrinks you, maybe use a poison bottle image from online
  case class GameStatePoisonIdea(score: Int, player: Point, food: Seq[Point], poison: Seq[Point])

  // Idea: Game only lasts for 30 seconds. Show timer, and show score at the end

  // Idea: Pause the game with spacebar

  /* Idea: Player chooses color (in command line before game starts)
  val colorInput = Util.readLine()
  val playerColor = colorInput match {
    case "red" => World.red
    ...
  }
  World(
    ...
    initialState.copy(playerColor = playerColor)
  )
   */

  // Idea: Turn the game into a simulation:
  // remove onKey, and program what the 'player' should do automatically based on the state in onTick
  // Try a random movement strategy to get started

  // Math down here

  def circlesIntersect(p1: Point, r1: Int, p2: Point, r2: Int): Boolean = {
    val center1 = Point(p1.x + r1, p1.y + r1)
    val center2 = Point(p2.x + r1, p2.y + r1)
    val distSq = (center1.x - center2.x) * (center1.x - center2.x) +
      (center1.y - center2.y) * (center1.y - center2.y)
    val radSumSq = (r1 + r2) * (r1 + r2)
    return (distSq <= radSumSq)
  }

  def pointInsideCircle(circlePoint: Point, radius: Int, point: Point): Boolean = {
    val centerX = circlePoint.x + radius
    val centerY = circlePoint.y + radius
    Math.pow(point.x - centerX, 2) + Math.pow(point.y - centerY, 2) < (radius * radius)
  }

}
