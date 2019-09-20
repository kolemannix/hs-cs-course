package solutions

object Unit2Strings {

  // Series of expressions intended to be run in the console

  // Strings

  "trouble".length
  "trouble".size

  def sizeOf(s: String): Int = s.size

  sizeOf("trouble") == "trouble".size

  "trouble".indexOf('t')
  // 0

  "trouble".indexOf('e')
  // 6

  "trouble".indexOf('o')
  // 2

  "trouble".indexOf("bl")
  // 4

  "trouble".indexOf('a')
  // -1

  "double trouble".replace("double", "triple")
  // > "triple bubble"

  "double trouble".replace("trouble", "bubble")
  // > "double bubble"

  "trouble".endsWith("le")

  "trouble".head
  // > 't': Char

  "trouble".last
  // > 'e': Char

  // Char - what strings are made of

  'c'.isLetter

  'c'.isLetterOrDigit

  'c'.isValidInt

  '3'.isValidInt

  'a'.toByte.toInt

}
