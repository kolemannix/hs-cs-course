package lib

import better.files.Resource
import lib.Markov.{ buildDatabase, generate }

object MarkovTest extends App {
  val dickensCorpus = Resource.getAsString("dickens.txt")
  val dickensDb = buildDatabase(2, dickensCorpus)
  (1 to 5) foreach { _ =>
    val sentence = generate(dickensDb, 30)
    println(sentence)
  }

}
