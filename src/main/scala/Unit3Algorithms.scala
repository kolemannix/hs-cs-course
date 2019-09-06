object Unit3Algorithms extends App {

  def fibonacci(n: Int): Int = {
    if (n == 0) 1
    else if (n == 1) 2
    else {
      fibonacci(n - 2) + fibonacci(n - 1)
    }
  }

  println(fibonacci(0))
  println(fibonacci(1))
  println(fibonacci(2))
  println(fibonacci(3))
  println(fibonacci(9))

  def isPrime(n: Int): Boolean = {
    // "To test N for primality, use 'trial division'.
    // Just divide by all of the primes less than the square root of N.
    ???
  }

  def primesLessThanN(n: Int): Seq[Int] = {
    // How to generate primes?
    ???
  }

}
