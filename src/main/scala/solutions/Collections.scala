package solutions

// Working with Array and Seq
object Collections extends App {

  // List and Seq are mostly interchangeable. Seq is more general. List is a specific type of Seq.
  val s1: Seq[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  assert(s1 == (1 to 10))
  println("s1: " + s1)

  // The 4th element of s1
  println("s1(3): " + s1(3))
  // The first element of s1. Is this always safe?
  println("s1.head: " + s1.head)

  assert(!s1.isEmpty)
  assert(s1.nonEmpty)

  // While head and last and indexing are useful, the most important thing we do with collections is go
  // through them one-by-one. This is called "iterating" over a collection.
  // We do this most often with "foreach" and "map"
  println("s1 map")
  s1.map(i => println("i is " + i))

  // A function 'from Int to Int' that adds 1 to the input
  val addOne: Int => Int = {
    x => x + 1
  }

  val s2 = s1.map(addOne)
  println("s2: " + s2)
  // We can also write the function "inline" - which means not defining it separately.
  s1.map(x => x - 1)
  assert(s2 == Seq(2, 3, 4, 5, 6, 7, 8, 9, 10, 11))

  // Arrays are a type of Seq as well.
  val a1: Seq[String] = Array("This", "is", "an", "array", "of", "strings")
  println("a1: " + a1)
  // Array is like Seq and List but it can be weird because it's older and comes from Java

  println(Seq("Hello", "World").map(s => s.head))

  println(Seq("Hello", "World").map(s => s.size))

  println(Seq("Hello", "World").map(s => s.contains("e")))

}
