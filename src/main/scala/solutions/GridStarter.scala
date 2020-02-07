package solutions

import scala.util.Try

import com.sksamuel.scrimage.Color
import lib.World
import lib.World._

object GridStarter extends App {

  val TILE_COUNT = 10

  val TILE_SIZE = 50

  val WIDTH = TILE_SIZE * TILE_COUNT
  val HEIGHT = TILE_SIZE * TILE_COUNT

  case class Tile(point: Point, color: Color)
  case class GameState(
    tiles: Seq[Seq[Tile]]
  )

  def onTick(state: GameState): GameState = {
    state
  }

  def draw(state: GameState): Scene = {
    val rectangles = state.tiles.flatten.map({ tile =>
      Rectangle(tile.point.x * TILE_SIZE, tile.point.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, color = tile.color)
    })
    Scene(rectangles)
  }

  def onTileClick(state: GameState, tile: Tile): GameState = {
    println(s"Clicked ${tile}")
    state
  }

  def onMouse(state: GameState, point: Point, button: Button): GameState = {
    Try {
      val x = point.x / TILE_SIZE
      val y = point.y / TILE_SIZE
      state.tiles(y)(x)
    }.fold(_ => state, { tile =>
      onTileClick(state, tile)
    })
  }

  val initialState = {
    val tiles =
      (0 to TILE_COUNT - 1).map { y =>
        (0 to TILE_COUNT - 1).map { x =>
          val color = if (y % 2 == 0) {
            if (x % 2 == 0) World.red else World.black
          } else {
            if (x % 2 == 1) World.red else World.black
          }
          Tile(Point(x, y), color)
        }
      }
    GameState(tiles)
  }

  World(
    name = "GridStarter",
    initial = initialState,
    width = WIDTH,
    height = HEIGHT,
    onTick = onTick,
    draw = draw,
    onMouse = onMouse
  )

}
