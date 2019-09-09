package solutions

import lib.Util.example
import lib.Crypto

object VigenereCipher extends App {
  def vigenereEncrypt(message: String, key: String): String = {
    message.toLowerCase.zipWithIndex.map { case (char, i) =>
      if (!char.isLetter) {
        char
      } else {
        val keyIndex = i % key.length
        val keyChar = key.charAt(keyIndex)
        val keyOffset = Crypto.letterPosition(keyChar)
        val encrypted = Crypto.encryptLetter(char, keyOffset)
        encrypted
      }
    }.mkString
  }

  def vigenereDecrypt(message: String, key: String): String = {
    message.toLowerCase.zipWithIndex.map { case (char, i) =>
      if (!char.isLetter) {
        char
      } else {
        val keyIndex = i % key.length
        val keyChar = key.charAt(keyIndex)
        val keyOffset = Crypto.letterPosition(keyChar)
        val decrypted = Crypto.decryptLetter(char, keyOffset)
        decrypted
      }
    }.mkString
  }

  example {
    val sample = "enemy has 5 howitzers"
    println("Sample: " + sample)
    val encrypted = vigenereEncrypt(sample, "supersecret")
    println("\tEncrypted: " + encrypted)
    val decrypted = vigenereDecrypt(encrypted, "supersecret")
    println("\tDecrypted: " + decrypted)
  }

}
