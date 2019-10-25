package solutions

import scala.util.Random

import better.files.Resource
import lib.Util

//case class BabyName(year: Int, name: String, percent: Double, gender: String)

object BabyNamesInClass extends App {

  val namesFile: String = Resource.getAsString("baby-names.csv")
  println("I loaded the file. It is this long " + namesFile.length)

  val lines: Array[String] = namesFile.split("\n")

  println("There are " + lines.length + " lines")

  val index = Random.nextInt(lines.length)

  println("Index is " + index)

  val randomName = lines(index)

  println("The name is [" + randomName + "]")

}
