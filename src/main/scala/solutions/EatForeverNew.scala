package solutions

import scala.concurrent.duration.FiniteDuration
import scala.util.Random

import com.sksamuel.scrimage.Color
import lib.World
import lib.World._

object EatForeverNew extends App {

  val PLAYER_SPEED = 3

  case class GameState(score: Int, player: Point, food: Seq[Point], poison: Seq[Point]) {
    def moveRight(): GameState = copy(player = Point(player.x + PLAYER_SPEED, player.y))
    def moveLeft(): GameState = copy(player = Point(player.x - PLAYER_SPEED, player.y))
    def moveUp(): GameState = copy(player = Point(player.x, player.y - PLAYER_SPEED))
    def moveDown(): GameState = copy(player = Point(player.x, player.y + PLAYER_SPEED))
  }
  val WIDTH = 600
  val HEIGHT = 600
  val center = Point(WIDTH / 2, HEIGHT / 2)

  val FOOD_RADIUS = 2
  val FOOD_SIZE = FOOD_RADIUS + FOOD_RADIUS

  val POISON_RADIUS = 5
  val POISON_SIZE = POISON_RADIUS + POISON_RADIUS

  val initialState = GameState(10, center, Nil, Nil)

  def onTick(state: GameState): GameState = {
    val newFood = if (Random.nextInt(12) == 1) Some(Point(Random.nextInt(WIDTH), Random.nextInt(HEIGHT))) else None
    val newPoison = if (Random.nextInt(24) == 1) Some(Point(Random.nextInt(WIDTH), Random.nextInt(HEIGHT))) else None
    val (foodEaten, foodNotEaten) = state.food.partition { foodPosition =>
      circlesIntersect(state.player, state.score / 2, foodPosition, FOOD_RADIUS)
      // pointInsideCircle(state.player, state.score / 2, food)
    }
    val (poisonEaten, poisonNotEaten) = state.poison.partition { poisonPosition =>
      circlesIntersect(state.player, state.score / 2, poisonPosition, POISON_RADIUS)
    }
    val newScore = Math.max(state.score + foodEaten.size - poisonEaten.size, 5)
    // Note: the poison does not get consumed right now. I kinda like the shrinking effect.
    // To fix it, poison below shoul be 'poison = poisonNotEaten ++ newPoison.toSeq'
    state.copy(food = foodNotEaten ++ newFood.toSeq, score = newScore, poison = state.poison ++ newPoison.toSeq)
  }

  val background = Rectangle(0, 0, WIDTH, HEIGHT, color = World.white)

  def draw(state: GameState): Scene = {
    val foods = state.food.map(food => Ellipse(food.x, food.y, FOOD_SIZE, FOOD_SIZE, color = World.black))
    val poisons = state.poison.map(poison => Ellipse(poison.x, poison.y, POISON_SIZE, POISON_SIZE, color = World.green))
    val player = Ellipse(state.player.x, state.player.y, state.score, state.score, color = World.red)
    Scene(
      Seq(background, player) ++ foods ++ poisons
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

  // Idea: X Change the width and height of the game; make it a corridor

  // Idea: X Print the score in top-center of the game

  // Idea: X Faster player speed

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

}
