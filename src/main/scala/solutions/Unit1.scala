package solutions

import scala.util.Try

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

    val numeratorPlus = Try { negativeB + discriminantSqrt }.toOption
    val numeratorMinus = Try { negativeB - discriminantSqrt }.toOption
    val denominator = 2.0 * a
    val root1 = numeratorPlus.map(n => n / denominator)
    val root2 = numeratorMinus.map(n => n / denominator)
    Seq(root1, root2).flatten
  }

}
