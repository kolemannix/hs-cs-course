package solutions

import scala.util.Try

import lib.Util

object Quadratic extends App {


  print("A: ")
  val a = Util.readLine().toDouble

  print("B: ")
  val b = Util.readLine().toDouble

  print("C: ")
  val c = Util.readLine().toDouble

  val negativeB = (b * -1)

  val discriminant = Math.sqrt((b * b) - (4 * a * c))
  println("Discriminant: " + discriminant)

  val numeratorPlus = (negativeB + discriminant)
  val numeratorMinus = (negativeB - discriminant)

  val denominator = 2 * a

  val resultPlus = Try { numeratorPlus / denominator }.toOption
  val resultMinus = Try { numeratorMinus / denominator }.toOption

  println("Intercept 1: " + resultPlus)
  println("Intercept 2: " + resultMinus)

  // a = 2, b = 5, c = -3
  // intercepts: -3, -2.97


}
