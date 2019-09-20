package solutions

import lib.Util

object GoOutside extends App {

  print("Is it raining? ")
  val rainingInput: String = Util.readLine()
  val isRaining: Boolean = rainingInput.toBoolean

  print("Is it Saturday? ")
  val isSaturday = Util.readLine().toBoolean

  if (isRaining) {
    println("Stay inside")
  } else {
    println("Go outside")
  }

}
