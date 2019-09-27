package solutions

import lib.Util

object GoOutside extends App {

  def responseToBoolean(response: String): Boolean = {
    if (response.trim.toLowerCase() == "yes") {
      true
    } else if (response.trim.toLowerCase() == "no") {
      false
    } else {
      response.toBoolean
    }
  }

  print("Is it raining? ")
  val rainingInput: String = Util.readLine()
  val isRaining: Boolean = responseToBoolean(rainingInput)

  if (isRaining) {
    println("Stay inside")
  } else {
    print("Is it Saturday? ")
    val isSaturday = responseToBoolean(Util.readLine())
    if (isSaturday) {
      println("Go outside")
    } else {
      println("Stay in school")
    }


  }

}
