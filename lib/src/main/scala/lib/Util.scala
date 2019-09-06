package lib

import java.io.{ BufferedReader, InputStreamReader }
import java.text.DecimalFormat
import scala.language.experimental.macros

import scala.reflect.macros.Context
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

  private lazy val reader = new BufferedReader(new InputStreamReader(System.in))
  def readLine(): String = {
    reader.readLine()
  }

  /* ---------------------------------- Macros ---------------------------------- */

  def debug[A](block: => A): A = macro debugImpl[A]

  def debugImpl[A: c.WeakTypeTag](c: Context)(block: c.Expr[A]): c.Expr[A] = {
    import c.universe._
    block.tree match {
//      case Literal(Constant(_)) =>
//        reify {
//          println(s"[DEBUG] ${block.splice}")
//          block.splice
//        }
      case _ =>
        println("SHOWCODE")
        val paramRep = showCode(block.tree)
        println(s"SHOWCODE ${paramRep}")
        val paramRepTree = Literal(Constant(paramRep))
        val paramRepExpr = c.Expr[String](paramRepTree)
        val reified = reify {
          println(s"[DEBUG] ${paramRepExpr.splice} = ${block.splice}")
          block.splice
        }
        reified
    }
  }

  def example[A](block: A): A = macro exampleImpl[A]
  def exampleImpl[A: c.WeakTypeTag](c: Context)(block: c.Expr[A]): c.Expr[A] = {
    import c.universe._
    reify {
      block.splice
    }
  }

  def check(code: Boolean): Unit = macro checkImpl
  def checkImpl(c: Context)(code: c.Expr[Boolean]): c.Expr[Unit] = {
    import c.universe._
    val paramRep = showCode(code.tree, printTypes = Some(false), printOwners = Some(false), printRootPkg = false, printIds = Some(false), printPositions = Some(false))
    val paramRepTree = Literal(Constant(paramRep))
    val paramRepExpr = c.Expr[String](paramRepTree)
    val reified = reify {
      println(s"Checking ${paramRepExpr.splice}: ${code.splice}")
    }
    reified
  }

}
