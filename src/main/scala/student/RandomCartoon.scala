package student

import scala.util.Random

import better.files.Resource

object RandomCartoon extends App {
  val namesFile: String = Resource.getAsString("cartoonNames")
  println("I loaded the file. It is this long " + namesFile.length)

  val lines: Array[String] = namesFile.split("\n")

  println("There are " + lines.length + " lines")

  val index = Random.nextInt(lines.length)

  println("Index is " + index)

  val randomName = lines(index)

  println("The cartoon is [" + randomName + "]")


}
