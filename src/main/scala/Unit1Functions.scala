import scala.util.Try
import Util._

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

    val numeratorPlus = Try { negativeB + discriminantSqrt }.toOption
    val numeratorMinus = Try { negativeB - discriminantSqrt }.toOption
    val denominator = 2.0 * a
    val root1 = numeratorPlus.map(n => n / denominator)
    val root2 = numeratorMinus.map(n => n / denominator)
    Seq(root1, root2).flatten
  }

  debug(quadratic(2, -5, -3))
  debug(fahrenheitToCelsius(-50))
  debug(Util.round(fahrenheitToCelsius(-50)))
  debug(fahrenheitToCelsius(100))
  debug(fahrenheitToCelsius(10))
  debug("asdf" ++ "fefef")
  debug(3.14 < 42)

}

object Unit1Exercises {

  // 1. Define and test a function that squares an integer

  // 2. Define and test a function that repeats a string. For example:
  //    Given "hello", it will return "hellohello"

  // 3. Define and test a function that converts a celsius temperature to fahrenheit

  // 4.

}
