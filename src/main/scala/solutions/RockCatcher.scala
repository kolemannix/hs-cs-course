package solutions

import scala.concurrent.duration.FiniteDuration

import com.sksamuel.scrimage.Image
import lib.{ Img, World }
import lib.World._

object RockCatcher extends App {

  case class GameState(rocks: Seq[Point])

  val initialState = GameState(Seq(Point(20, 0)))

  def onTick(state: GameState): GameState = {
    val newRocks = state.rocks.map(rock => Point(rock.x, (rock.y + 1) % 600))
    state.copy(rocks = newRocks)
  }

  // val rockImage = Image.fromResource("/image/rock.png").scaleTo(100, 100)
  val rockImage = Img.loadImageUrl(
    "https://akm-img-a-in.tosshub.com/indiatoday/images/story/201705/dwayne-johnson-us-president---story_647_051217113745.jpg"
  ).scaleTo(100, 100)
  val desert = Image.fromResource("/image/desert.jpg")
  val background = Sprite(desert, Point.origin, 800, 600)

  def draw(state: GameState): Scene = {
    val rockSprites = state.rocks.map(rock => Sprite(rockImage, rock))
    val statePrint = Text(50, 40, state.toString, 24)
    Scene(
      Seq(background, statePrint) ++ rockSprites
    )
  }

  World(
    name = "RockCatcher",
    width = 800,
    height = 600,
    tickInterval = FiniteDuration(10, "ms"),
    initial = initialState,
    onTick = onTick,
    draw = draw
  )

}
