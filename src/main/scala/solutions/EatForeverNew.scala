package solutions

import scala.concurrent.duration.FiniteDuration
import scala.util.Random
import scala.io.StdIn

import com.sksamuel.scrimage.Color
import lib.World
import lib.World._

object EatForeverNew extends App {

  val PLAYER_SPEED = 3


  case class Poison(point: Point, age: Int) {
    def aged(): Poison = copy(age = age + 1)
  }
  case class GameState(
    score: Int,
    player: Point,
    food: Seq[Point],
    poison: Seq[Poison],
    player1Color: Color,
    paused: Boolean
  ) {
    def moveRight(): GameState = copy(player = Point(player.x + PLAYER_SPEED, player.y))
    def moveLeft(): GameState = copy(player = Point(player.x - PLAYER_SPEED, player.y))
    def moveUp(): GameState = copy(player = Point(player.x, player.y - PLAYER_SPEED))
    def moveDown(): GameState = copy(player = Point(player.x, player.y + PLAYER_SPEED))
  }
  val WIDTH = 900
  val HEIGHT = 800
  val center = Point(WIDTH / 2, HEIGHT / 2)

  val FOOD_RADIUS = 3
  val FOOD_SIZE = FOOD_RADIUS + FOOD_RADIUS

  val POISON_RADIUS = 5
  val POISON_SIZE = POISON_RADIUS + POISON_RADIUS
  val POISON_MAX_AGE = 1000
  val POISON_PENALTY = 10

  val initialState = GameState(10, center, Nil, Nil, World.red, false)

  def randomPoint(size: Int): Point = Point(Random.nextInt(WIDTH - (size / 2)), Random.nextInt(HEIGHT - (size / 2)))

  def onTick(state: GameState): GameState = {
    if (state.paused) {
      state
    } else {
      val newFood = if (Random.nextInt(12) == 1) Some(randomPoint(FOOD_SIZE)) else None
      val newPoison = if (Random.nextInt(24) == 1) Some(Poison(randomPoint(POISON_SIZE), 0)) else None

      val (foodEaten, foodNotEaten) = state.food.partition { foodPosition =>
        circlesIntersect(state.player, state.score / 2, foodPosition, FOOD_RADIUS)
      }
      val (poisonEaten, poisonNotEaten) = state.poison.partition { poison =>
        circlesIntersect(state.player, state.score / 2, poison.point, POISON_RADIUS)
      }
      val remainingPoison = poisonNotEaten.map(poison => poison.aged()).filter(_.age <= POISON_MAX_AGE)
      val newScore = Math.max(state.score + foodEaten.size - (poisonEaten.size * POISON_PENALTY), 5)
      state.copy(
        food = foodNotEaten ++ newFood.toSeq,
        score = newScore,
        poison = poisonNotEaten ++ newPoison.toSeq
      )
    }
  }

  val background = Rectangle(0, 0, WIDTH, HEIGHT, color = World.white)
  val blackGround = Rectangle(0, 0, WIDTH, HEIGHT, color = World.black)

  def draw(state: GameState): Scene = {
    if (state.paused) {
      Scene(Seq(blackGround))
    } else {
      val foods = state.food.map(food => Ellipse(food.x, food.y, FOOD_SIZE, FOOD_SIZE, color = World.black))
      val poisons = state.poison.map(poison => Ellipse(poison.point.x, poison.point.y, POISON_SIZE, POISON_SIZE, color = World.green))
      val player = Ellipse(state.player.x, state.player.y, state.score, state.score, color = state.player1Color)
      val pausedText = if (state.paused) Seq(Text(20, 20, "Paused", 24)) else Nil
      Scene(
        Seq(background, player) ++ foods ++ poisons ++ pausedText
      )
    }
  }

  def onKey(state: GameState, key: Key): GameState = {
    if (state.paused) {
      if (key == Space) state.copy(paused = false)
      else state
    } else {
      key match {
        case RightArrow => state.moveRight()
        case LeftArrow => state.moveLeft()
        case UpArrow => state.moveUp()
        case DownArrow => state.moveDown()
        // case Space => state.copy(paused = false)
        case Letter('p') => state.copy(paused = true)
        case _ => state
      }
    }


  }

  print("Player 1 color: ")
  val color = StdIn.readLine() match {
    case "red" => World.red
    case "blue" => World.blue
    case "yellow" | "y" => World.yellow
    case _ => World.red
  }
  World(
    name = "EatForever",
    width = WIDTH,
    height = HEIGHT,
    tickInterval = FiniteDuration(25, "ms"),
    initial = initialState.copy(player1Color = color),
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
