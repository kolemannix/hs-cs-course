package solutions

import scala.util.Random

import better.files.Resource
import lib.Util

case class BabyName(year: Int, name: String, percent: Double, gender: String)

object BabyNames extends App {
  val columns = Seq("year", "name", "percent", "gender")

  val namesFile = Resource.getAsString("baby-names.csv")
  val lines = namesFile.split("\n").tail
  val babyNames = lines.map { line =>
    val parts = line.split(",")
    val year = parts(0).toInt
    val name = parts(1).replaceAll("\"", "")
    val percent = parts(2).toDouble
    val gender = parts(3).replaceAll("\"", "")
    BabyName(year, name, percent, gender)
  }
  val girlNames = babyNames.filter { name =>
    name.gender == "girl"
  }
  val boyNames = babyNames.filter { name =>
    name.gender == "boy"
  }

  print("Boy or Girl name [b/g]: ")
  val genderInput = Util.readLine()
  val isGirl = genderInput.trim.toLowerCase() == "g"

  if (isGirl) {
    println(s"Choosing a number between 0 and ${girlNames.size}...")
    val index = Random.nextInt(girlNames.size)
    println(s"Chose: ${index}")
    val chosenName = girlNames(index)
    println(s"Try the ${chosenName.gender} name ${chosenName.name} from the year ${chosenName.year}")
  } else {
    println(s"Choosing a number between 0 and ${boyNames.size}...")
    val index = Random.nextInt(boyNames.size)
    println(s"Chose: ${index}")
    val chosenName = boyNames(index)
    println(s"Try the ${chosenName.gender} name ${chosenName.name} from the year ${chosenName.year}")
  }
}
