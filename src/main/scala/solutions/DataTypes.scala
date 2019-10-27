package solutions

object DataTypes extends App {

  /*
  So far, we have worked with built-in simple types like
  Int, Boolean, Double, String, and Char.

  Today we'll learn how to combine those types into more complex ones, that let us solve bigger problems

  We'll discuss the idea of data representation
   */

  case class Point(x: Double, y: Double)
  case class Student(name: String, age: Int, gpa: Double)

  val origin = Point(0, 0)

  println("Origin is " + origin)

  // Game of basketball
  case class Game(
    scoreboard: Scoreboard,
    playerPositions: Seq[Point],
  )
  case class Scoreboard(
    team1Score: Int,
    team2Score: Int,
    currentQuarter: Int,
    timeLeft: Double,
    possessionArrow: Possession,
  )

  trait Possession
  object Home extends Possession
  object Away extends Possession

  // Pictures
  case class Pixel(red: Int, green: Int, blue: Int)
  case class Image(pixels: Seq[Seq[Pixel]])
}
