import lib.Markov.{ buildDatabase, generate }
import lib.Util.example
import better.files.Resource

class MarkovTest {
  val dickensCorpus = Resource.getAsString("literature/dickens.txt")
  val dickensDb = buildDatabase(3, dickensCorpus)
  (1 to 5) foreach { _ =>
    val sentence = generate(dickensDb, 30)
    println(sentence)
  }

}
