package lib

import scala.util.Random

object Crypto {

  def alphabet = "abcdefghijklmnopqrstuvwxyz"
  lazy val scrambledAlphabet: String = alphabet.sortBy(_ => Random.nextBoolean())

  def letterPosition(l: Char): Int = {
    alphabet.indexOf(l)
  }

  def encryptLetter(letter: Char, offset: Int): Char = {
    val position = letterPosition(letter)
    val shifted = (position + offset) % alphabet.length
    alphabet.charAt(shifted)
  }

  def decryptLetter(letter: Char, offset: Int): Char = {
    val position = letterPosition(letter)
    val shifted = (position - offset) % alphabet.length
    val shiftedAgain = if (shifted < 0) shifted + alphabet.length else shifted
    alphabet.charAt(shiftedAgain)
  }


}
