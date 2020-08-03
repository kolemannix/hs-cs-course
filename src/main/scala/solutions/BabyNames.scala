package solutions

import scala.util.Random

import better.files.Resource
import lib.Util
import scala.io.StdIn

case class BabyName(year: Int, name: String, percent: Double, gender: String)

object BabyNames extends App {
  val columns = Seq("year", "name", "percent", "gender")

  val startLoad = System.currentTimeMillis()
  val namesFile = Resource.getAsString("baby-names.csv")
  val loadTime = System.currentTimeMillis() - startLoad
  println(s"Loaded names file in ${loadTime} milliseconds")
  val lines = namesFile.split("\n").tail.toSeq
  val babyNames: Seq[BabyName] = lines.map { line =>
    val parts = line.split(",")
    val year = parts(0).toInt
    val name = parts(1)
    val percent = parts(2).toDouble
    val gender = parts(3)
    BabyName(year, name, percent, gender)
  }

  val isGirl: BabyName => Boolean = { name => name.gender == "girl" }
  val isBoy: BabyName => Boolean = { name => name.gender == "boy" }

  val girlNames = babyNames.filter(isGirl)
  val boyNames = babyNames.filter(isBoy)

  print("Boy or Girl name [b/g]: ")
  val genderInput = StdIn.readLine()
  val pickGirl = genderInput.trim.toLowerCase() == "g"

  if (pickGirl) {
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
