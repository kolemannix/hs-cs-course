package student

import better.files.Resource

object CountOcurrences extends App {

  val input = Resource.getAsString("literature/artofwar.txt")

  val lines = input.split("w+").toSeq

  val linesAboutEnemies = lines.filter(s => s.toLowerCase.contains("enemy"))

  println(linesAboutEnemies.mkString("\n"))
  println("Lines containing 'enemy': " + linesAboutEnemies.size)

}

object FindNouns extends App {
  val nouns = Seq(
    "book", "tree", "dog", "ball", "school", "table"
  )

  val input =
    """
      |Pretend this is a book about a dog who finds a tree at school
      |""".stripMargin

  val isNoun = {
    word: String => nouns.contains(word)
  }

  val inputWords = input.split(" ").toSeq.map(w => w.trim)
  println(inputWords)

  val foundNouns = inputWords.filter(isNoun)

  println("Found nouns: " + foundNouns)
}
