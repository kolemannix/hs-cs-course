package solutions

import scala.util.Random

import lib.Util._

object CaesarInClass extends App {

  val alphabet = "abcdefghijklmnopqrstuvwxyz"
  def scrambledAlphabet = alphabet.sortBy(_ => Random.nextBoolean())

  def letterToNumber(c: Char): Int = alphabet.indexOf(c)
  def numberToLetter(n: Int): Char = alphabet.charAt(n)

  val encode = { original: Char =>
    val output = original
    println(s"[INFO] Mapping '${original}' to '${output}'")
    output
  }

  def caesarEncode(input: String, key: Int): String = {
    // We want to 'map' the input to a new string.
    // We can write the mapping function 'inline', as below
    input.filterNot({ c => c.isSpaceChar }).map({ original =>
      val output = original
      println(s"[INFO] Mapping '${original}' to ${output}")
      output
    })
    // Or write it separately and use its name here
    input.map(encode)
  }



  def vigenereEncode2(input: String, key: String): String = {
    def getTheOffset(i: Int): Int = {
      val char = key.charAt(i % key.length)
      val index = alphabet.indexOf(char)
      println(s"Using key char ${char}, ${index}")
      index
    }

    input.filterNot(_.isWhitespace).zipWithIndex.map({ case (original: Char, n: Int) =>
      val offset = getTheOffset(n)
      // Do normal caesar shift
      val shifted = (alphabet.indexOf(original) + offset) % 26
      val output = alphabet.charAt(shifted)
      output
    }).mkString
  }

  def vigenereEncode(input: String, key: String): String = {
    input.filterNot(_.isWhitespace).zipWithIndex.map({ case (original: Char, n: Int) =>
      val keyIndex = (n % key.length)
      val keyChar = key.charAt(keyIndex)
      val shift = alphabet.indexOf(keyChar)

      val originalIndex = alphabet.indexOf(original)

      val newIndex = (originalIndex + shift) % 26

      val output = alphabet.charAt(newIndex)
      original
    }).mkString
  }
  print("Message: ")
  val input = "attack at midnight"

  // val output = caesarEncode(input, 3)
  val vigenereOutput = vigenereEncode(input, "digger")

  // println("Output: " + output)
  println("Vigenere Output: " + vigenereOutput)


  // Gives the position (index) of 'e' in the alphabet
  val indexOfE = alphabet.indexOf('e')
  // Gives the letter at position (index) 10 in the alphabet
  val letterAtPosition6 = alphabet.charAt(10)

}
