import KnixLib._

object Tour extends App {
  // Day One
  // Comments

  // Strings
  println(4)
  println("Hello, World!")

  assert(2 == 2)
  assert(2 + 2 == 5)
  assert(5.0 == 5)
  assert(4 + 5 == 9)
  assert(1 / 3 == 2 / 6)
  assert(9 - 3 == 6)
  assert(3 * 4 == 12)

  assert(true == true)
  assert(false == false)

  if (3 > 2)
    println("yes!")
  else
    println("no!")

  // Strings

  println("We can use 'single' quotes freely")
  println("But we must 'escape' double quotes, like so:\n\t\"To be, or not to be, that is the question\"")

  val stringOne = "Hello, World!"
  // What is '.toUpperCase()'
  val stringTwo = stringOne.toUpperCase()

  println(stringTwo)
  // Variables - They are how we ask the computer to keep track of data

  var x = 10 // I want a variable named 'x', and it starts off as 10
  println(x.getClass)
  x = 12
  println(x)

  // Does not work!
  // x = "chicken"

  assert(x == 12)

}