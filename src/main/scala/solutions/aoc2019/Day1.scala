package solutions.aoc2019

import better.files.Resource

object Day1 extends App {

  val input = Resource.getAsString("aoc2019/input1.txt")

  def calculateRequirements(module: String): Int = {
    (module.toInt / 3) - 2
  }

  println("Requirements for 12: " + calculateRequirements("12"))
  println("Requirements for 14: " + calculateRequirements("14"))
  println("Requirements for 1969: " + calculateRequirements("1969"))
  println("Requirements for 100756: " + calculateRequirements("100756"))

  val allRequirements: Array[Int] = input.split("\n").map { line =>
    calculateRequirements(line)
  }

  println("Solution: " + allRequirements.sum)
}
