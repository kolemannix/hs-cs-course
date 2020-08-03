package solutions

import scala.io.StdIn

object ChangeCountr extends App {

  println("Welcome to ChangeCountr, the next Killer App.")

  print("Number of quarters: ")
  val quartersInput = StdIn.readLine()
  val quarters = quartersInput.toInt

  print("Number of dimes: ")
  val dimesInput = StdIn.readLine()
  val dimes = dimesInput.toInt

  print("Number of nickels: ")
  val nickelsInput = StdIn.readLine()
  val nickels = nickelsInput.toInt

  print("Number of pennies: ")
  val penniesInput = StdIn.readLine()
  val pennies = penniesInput.toInt

  val cents = (quarters * 25) + (dimes * 10) + (nickels * 5) + pennies
  println("You've got " + cents + " cents")
}

object ChangeCountr2 extends App {

  println("Welcome to ChangeCountr, the next Killer App.")

  def computeCents(quarters: Int, dimes: Int, nickels: Int, pennies: Int): Int = {
    (quarters * 25) + (dimes * 10) + (nickels * 5) + pennies
  }

  def formatCents(input: Int): String = {
    val dollars = input / 100
    val cents = input % 100
    val centsFormatted = cents.formatted("%02d")
    dollars + "." + centsFormatted
  }

  def collect(prompt: String): Int = {
    print(prompt + ": ")
    val input = StdIn.readLine()
    input.toInt
  }

  val quarters = collect("Number of quarters")
  val dimes = collect("Number of dimes")
  val nickels = collect("Number of nickels")
  val pennies = collect("Number of pennies")

  val cents = computeCents(quarters, dimes, nickels, pennies)
  println("You've got $" + formatCents(cents))
}

// Write a program, using ChangeCountr as a template, that reads
// input from the user and performs some useful computation
// You can use one of the functions from Unit1Functions or come up with your own
