import Util._

object Unit2Crypto extends App {

  val sample = "Secret: abcdefghijklmnopqrstuvwxyz"

  val alphabet = "abcdefghijklmnopqrstuvwxyz"

  def caesarEncode(message: String, offset: Int): String = {
    val mappedCharacters = message.toLowerCase().map { char =>
      if (!char.isLetter) {
        char
      } else {
        // println(s"CHAR: ${char}")
        val pos = alphabet.indexOf(char)
        // println(s"\tpos: ${pos}")
        val shifted = (pos + offset) % alphabet.length
        val newPos = if (shifted < 0) {
          alphabet.length + shifted
        } else {
          shifted
        }
        // println(s"\tnewPos: ${newPos}")
        val newChar = alphabet.charAt(newPos)
        newChar
      }
    }
    mappedCharacters.mkString
  }

  example {
    val result1 = caesarEncode(sample, 3)
    println(result1)
    val decode = caesarEncode(result1, -3)
    println(decode)
  }

}
