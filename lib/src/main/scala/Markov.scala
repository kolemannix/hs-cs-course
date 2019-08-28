import java.time.Instant

import better.files.Resource

import Util._

object Markov extends App {

  val sampleCorpus: String =
    """
      |The sky is blue.
      |The kid jumped off.
      |The car is fast.
      |The sky is cloudy.
      |The sky is sunny.
      |The sky is foggy.
    """.stripMargin

  type Db = Map[Seq[String], Entry]

  case class PrefixedWord(prefix: Seq[String], word: String)
  case class Entry(prefix: Seq[String], followers: Map[String, Int]) {
    override def toString: String = s"[${prefix.mkString(",")}]: ${followers}"

    def selectPrefixedWord(): PrefixedWord = PrefixedWord(prefix, weightedSelection())

    def weightedSelection(): String = {
      val p = scala.util.Random.nextDouble
      val it = followers.iterator
      var accum = 0.0
      while (it.hasNext) {
        val (item, itemProb) = it.next
        accum += itemProb
        if (accum >= p)
          return item // return so that we don't have to search through the whole distribution
      }
      throw new RuntimeException("Weighted random error: should be unreachable")
    }
  }

  def parseCorpus(input: String): Seq[String] = {
    input.split("\\s+").filter(_.nonEmpty).map(_.trim)
  }

  def generateTuples(n: Int, corpus: Seq[String]): Iterator[(Seq[String], String)] = {
    val windows = corpus.sliding(n, 1)
    windows.map { words =>
      val key = words.dropRight(1)
      val value = words.last
      (key, value)
    }
  }

  def indexFrequencies(database: Db, key: Seq[String], value: String): Db = {
    val newEntry = database.get(key) match {
      case Some(entry@Entry(_, followers)) =>
        val newFreq = followers.get(value) match {
          case Some(freq) => freq + 1
          case None => 1
        }
        entry.copy(followers = entry.followers + (value -> newFreq))
      case None =>
        Entry(key, Map(value -> 1))
    }
    database + (key -> newEntry)
  }

  def buildDatabase(nGramLength: Int, corpus: String): Db = {
    val start = Instant.now
    val tuples = generateTuples(nGramLength + 1, parseCorpus(corpus))
    val db: Db = Map.empty
    val result = tuples.foldLeft(Map.empty: Db) { case (db, (prefix, word)) =>
      indexFrequencies(db, prefix, word)
    }
    println(s"Build Database completed on ${corpus.length} byte corpus in ${Instant.now.toEpochMilli - start.toEpochMilli}ms")
    result
  }

  def nextWord(db: Db, prefix: Seq[String]): Option[PrefixedWord] = {
    db.get(prefix) match {
      case Some(entry) =>
        val newWord = entry.weightedSelection()
        val newPrefix = prefix.tail :+ newWord
        Some(PrefixedWord(newPrefix, newWord))
      case None =>
        None
    }
  }

  def chooseSeed(db: Db): PrefixedWord = {
    // FIXME: Could throw
    val capitalEntries = db.values.filter(_.prefix.head.head.isUpper)
    if (capitalEntries.isEmpty) {
      // No capitals, just take the very first word we can find in the database
      // FIXME: Could throw, disallow empty DBs
      val entry = db.head._2
      entry.selectPrefixedWord()
    } else {
      // Choose a random prefixed word
      capitalEntries.randomElement().selectPrefixedWord()
    }
  }

  def generateText(db: Db, maxWords: Int): Seq[String] = {
    val seed = chooseSeed(db)
    var words: Seq[String] = seed.prefix
    var lastPrefix = seed.prefix
    while (true) {
      if (words.length >= maxWords) {
        return words
      }
      nextWord(db, lastPrefix) match {
        case Some(prefixedWord) =>
          // println(s"Adding entry: ${prefixedWord}")
          lastPrefix = prefixedWord.prefix
          words = words :+ prefixedWord.word
        case None =>
          return words
      }
    }
    throw new RuntimeException("Unreachable")
  }

  def generate(db: Db, maxWords: Int): String = {
    val sentenceSeq = generateText(db, maxWords)
    sentenceSeq.mkString(" ")
  }

  /*
  ;; (defn crossover [corpus-a corpus-b n]
  ;;   (let [db-a (build-database corpus-a n)
  ;;         db-b (build-database corpus-b n)]
  ;;     ))
     */

}
