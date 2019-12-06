package lib

import com.sksamuel.scrimage.Color
import lib.World.{ BoundingBox, Button, Image, Point, Rectangle, Scene }

object TicTacToeWorld extends App {

  sealed trait Mark
  case object X extends Mark
  case object O extends Mark
  case object Empty extends Mark

  case class State(
    turn: Boolean,
    pieces: Seq[Seq[(Mark, BoundingBox)]],
  )
  def empty = State(true, Seq(
    Seq((Empty, boundingBox(0, 0)), (Empty, boundingBox(0, 1)), (Empty, boundingBox(0, 2))),
    Seq((Empty, boundingBox(1, 0)), (Empty, boundingBox(1, 1)), (Empty, boundingBox(1, 2))),
    Seq((Empty, boundingBox(2, 0)), (Empty, boundingBox(2, 1)), (Empty, boundingBox(2, 2)))
  ))

  val totalWidth = 600
  val totalHeight = 400

  val lineLength = 300
  val space = lineLength / 3

  val boardStartX = (totalWidth / 2) - (lineLength / 2)
  val boardStartY = (totalHeight / 2) - (lineLength / 2)

  val flower = Image.fromResource("/image/flower.png", Point.origin, 600, 400)

  def boundingBox(row: Int, col: Int): BoundingBox = {
    val scaleX = (row * space)
    val scaleY = (col * space)
    BoundingBox.fromRect(boardStartX + scaleX, scaleY + boardStartY, space, space)
  }
  def boardLines(boardStartX: Int, boardStartY: Int) = {
    val boardLines = Seq(
      Rectangle(boardStartX + space, boardStartY, 5, lineLength),
      Rectangle(boardStartX + (space * 2), boardStartY, 5, lineLength),
      Rectangle(boardStartX, boardStartY + space, lineLength, 5),
      Rectangle(boardStartX, boardStartY + (space * 2), lineLength, 5)
    )
    boardLines
  }


  def draw(state: State): Scene = {
    Scene(
      drawables = boardLines(boardStartX, boardStartY)
    )
  }

  def onTick(state: State): State = {
    state
  }

  def onMouse(state: State, point: Point, mouse: Button): State = {
    println(s"Mouse ${mouse} ${point}")
    val target = state.pieces.flatten.find { case (mark, bb) =>
      bb.contains(point)
    }
//    target match {
//      case Some(t) => t.
//      case None =>
//    }
    println(s"Target! ${target}")
    state
  }

  val world = World[State](
    initial = empty,
    onTick = onTick,
    onMouse = onMouse,
    draw = draw
  )

}
