package solutions

import scala.util.Random

import lib.Util._

object CaesarCipher extends App {

  def alphabet = "abcdefghijklmnopqrstuvwxyz"
  lazy val scrambledAlphabet = alphabet.sortBy(_ => Random.nextBoolean())

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

  def caesarEncrypt(message: String, offset: Int): String = {
    require(offset > 0, "Offset must be greater than zero!")

    val encryptedMessage = message.toLowerCase.map { char =>
      if (!char.isLetter) {
        char
      } else {
        encryptLetter(char, offset)
      }
    }
    encryptedMessage
  }

  def caesarDecrypt(message: String, offset: Int): String = {
    require(offset > 0, "Offset must be greater than zero!")

    val decryptedMessage = message.toLowerCase.map { char =>
      if (!char.isLetter) {
        char
      } else {
        decryptLetter(char, offset)
      }
    }
    decryptedMessage
  }

  example {
    val sample = "enemy has 5 howitzers"
    println("Sample: " + sample)
    val encrypted = caesarEncrypt(sample, 3)
    println("\tEncrypted: " + encrypted)
    val decrypted = caesarDecrypt(encrypted, 3)
    println("\tDecrypted: " + decrypted)
  }

  example {
    // Does it work with a scrambled alphabet?
    val sample = "i am 9 years old"
    println("Sample: " + sample)
    val encrypted = caesarEncrypt(sample, 3)
    println("\tEncrypted: " + encrypted)
    val decrypted = caesarDecrypt(encrypted, 3)
    println("\tDecrypted: " + decrypted)

  }

  def caesarEncryptedecrypt(message: String, offset: Int): String = {
    val mappedCharacters = message.toLowerCase().map { char =>
      if (!char.isLetter) {
        char
      } else {
        val position = letterPosition(char)
        val shifted = (position + offset) % alphabet.length
        val newPos = if (shifted < 0) {
          shifted + alphabet.length
        } else {
          shifted
        }
        val newChar = alphabet.charAt(newPos)
        newChar
      }
    }
    mappedCharacters
  }
}
