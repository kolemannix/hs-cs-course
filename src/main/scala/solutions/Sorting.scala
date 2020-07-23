package solutions

object Sorting extends App {

  // Swaps elements recursively - doesn't finish the job!
  def sort(l: Seq[Int]): Seq[Int] = {
    println(s"Sorting [${l.mkString(",")}]")
    val first = l.lift(0)
    val second = l.lift(1)
    (first, second) match {
      case (Some(a), Some(b)) =>
        if (b < a) b +: sort(a +: l.drop(2))
        else a +: sort(l.drop(1))
      case _ => l
    }
  }

  println(sort(sort(Seq(3,2,1))))

}
