import scala.util.Try

import lib.DebugMacro
import org.scalatest.Assertions._

object Unit1Expressions extends App {

  // Numbers
  assert(1 + 1 == 2)

  println(25 / 5)

  println(27 / 5)

  println(27.0 / 5.0)

  println(Try {25 / 0})

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

  def isPositive(x: Int) = {
    if (x > 0) true else false
  }

  def concatenate(s1: String, s2: String) = {
    // s1.concat(s2)
    s1 ++ s2
  }

  def fahrenheitToCelsius(t: Double) = {
    // T(°C) = (T(°F) - 32) × 5/9
    (t - 32.0) * (5.0 / 9.0)
  }

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

object Unit1Exercises {

  // 1. Define and test a function that squares an integer

  // 2. Define and test a function that repeats a string. For example:
  //    Given "hello", it will return "hellohello"

  // 3. Define and test a function that converts a celsius temperature to fahrenheit

  // 4. Define a function that solves the quadratic formula, given a, b, and c
  def quadratic(a: Double, b: Double, c: Double): Seq[Double] = {
    ???
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

}
