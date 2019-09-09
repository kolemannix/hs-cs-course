package solutions

import scala.util.Try

import lib.Util
import org.scalatest.Assertions._

object Unit1Expressions extends App {

  // Numbers
  assert(1 + 1 == 2)

  println(25 / 5)

  println(27 / 5)

  println(27.0 / 5.0)

  // An approximation. How would we repesent true fractions?
  println(1.0 / 3.0)

  println(Try { 25 / 0 })

  println(4 > 2)

  // Booleans
  println(true == false)

  println(true == true)

  if (4 > 2) {
    println("yes")
  } else {
    println("no")
  }

  if (false || true) {
    println("yes")
  } else {
    println("no")
  }

  // Strings
  println("Hello, World")

  println("Hello, World".toUpperCase)
  println("Hello, " + "World")

  println("Hello".head)
  println("Hello".tail)
  println("Hello".last)
}

object Unit1Functions extends App {

  def plus3(x: Int): Int = {
    x + 3
  }

  def multiply(a: Int, b: Int): Int = {
    a * b
  }

  def isPositive(x: Int) = {
    if (x > 0) true else false
  }

  def isDivisibleBy4(x: Int): Boolean = {
    x % 4 == 0
  }

  def mystery(x: Int, y: Int): Boolean = {
    x % y == 0
  }

  def feetToInches(feet: Int): Int = {
    feet * 12
  }

  def inchesToFeet(x: Int): (Int, Int) = {
    val feet = x / 12
    val inches = x % 12
    (feet, inches)
  }

  def inchesToFeetDecimal(x: Int): Double = {
    val feet = x / 12
    val inches = x % 12
    val fraction = inches.toDouble / 12.0
    feet + fraction
  }

  def kilometersToMiles(kilos: Double): Double = {
    kilos * 0.621371
  }

  def poundsToKilograms(lbs: Double): Double = {
    lbs * 0.453592
  }

  def fahrenheitToCelsius(t: Double) = {
    // T(°C) = (T(°F) - 32) × 5/9
    (t - 32.0) * (5.0 / 9.0)
  }

  def numberToString(i: Int): String = {
    i.toString
  }

  def stringToInt(text: String): Int = {
    text.toInt
  }
  stringToInt("453")
  // stringToInt("uh oh")

  def quadratic(a: Double, b: Double, c: Double): Seq[Double] = {
    // -b
    val negativeB = b * -1.0

    // b^2 - 4ac
    val discriminant = (b * b) - (4.0 * a * c)
    val discriminantSqrt = Math.sqrt(discriminant)

    val numeratorPlus = Try {negativeB + discriminantSqrt}.toOption
    val numeratorMinus = Try {negativeB - discriminantSqrt}.toOption
    val denominator = 2.0 * a
    val root1 = numeratorPlus.map(n => n / denominator)
    val root2 = numeratorMinus.map(n => n / denominator)
    Seq(root1, root2).flatten
  }

  println(quadratic(2, -5, -3))
  println(fahrenheitToCelsius(-50))
  println(Util.round(fahrenheitToCelsius(-50)))
  println(fahrenheitToCelsius(100))
  println(fahrenheitToCelsius(10))

}
