import ChangeCountr2.{ dimes, nickels, quarters }
import lib.Util

object ChangeCountr extends App {

  println("Welcome to ChangeCountr, the next Killer App.")

  print("Number of quarters: ")
  val quartersInput = Util.readLine()
  val quarters = quartersInput.toInt

  print("Number of dimes: ")
  val dimesInput = Util.readLine()
  val dimes = dimesInput.toInt

  print("Number of nickels: ")
  val nickelsInput = Util.readLine()
  val nickels = nickelsInput.toInt

  print("Number of pennies: ")
  val penniesInput = Util.readLine()
  val pennies = penniesInput.toInt

  val result = Util.debug((quarters * 25) + (dimes * 10) + (nickels * 5) + pennies)
  println(s"You've got ${result} cents")
}

object ChangeCountr2 extends App {

  println("Welcome to ChangeCountr, the next Killer App.")

  def computeCents(quarters: Int, dimes: Int, nickels: Int, pennies: Int): Int = {
    (quarters * 25) + (dimes * 10) + (nickels * 5) + pennies
  }

  def collect(prompt: String): Int = {
    print(prompt + ": ")
    val input = Util.readLine()
    input.toInt
  }

  val quarters = collect("Number of quarters: ")
  val dimes = collect("Number of dimes: ")
  val nickels = collect("Number of nickels: ")
  val pennies = collect("Number of pennies: ")

  val result = computeCents(quarters, dimes, nickels, pennies)
  println(s"You've got ${result} cents")
}

// Write a program, using ChangeCountr as a template, that reads
// input from the user and performs some useful computation
// You can use one of the functions from Unit1Functions or come up with your own
