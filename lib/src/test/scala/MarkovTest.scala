import lib.Markov.{ buildDatabase, generate }
import lib.Util.example
import better.files.Resource

object MarkovTest extends App {
  val dickensCorpus = Resource.getAsString("dickens.txt")
  val dickensDb = buildDatabase(2, dickensCorpus)
  (1 to 5) foreach { _ =>
    val sentence = generate(dickensDb, 30)
    println(sentence)
  }

}
