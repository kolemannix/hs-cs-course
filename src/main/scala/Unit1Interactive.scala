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
