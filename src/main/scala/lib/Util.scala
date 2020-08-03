package lib

import java.io.{ BufferedReader, InputStreamReader }
import java.text.DecimalFormat

import scala.util.Random

object Util {

  def typeOf[A](a: A): String = {
    a.getClass.getTypeName
  }

  def round(d: Double, places: Int = 2): String = {
    val zeroes = List.fill(places)("#").mkString
    val format = new DecimalFormat(s"#.${zeroes}")
    format.format(d)
  }

  def getRandomElement[A](s: Iterable[A]): A = {
    require(s.nonEmpty, "Cannot get random element of empty collection")
    val index = Random.nextInt(s.size)
    s.iterator.drop(index).next
  }
  implicit class IterableOps[A](val s: Iterable[A]) extends AnyVal {
    def randomElement(): A = getRandomElement(s)
  }

}
