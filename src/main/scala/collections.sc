List(1, 2, 3, 4, 9001)

res0.size
res0.length

res0(0)
res0(2)

val addOne: Int => Int = {
  i =>
    println("Adding 1 to " + i)
    i + 1
}

val isEven = { x: Int => x % 2 == 0 }

val mappedList = res0
  .map(addOne)
  .filter(isEven)

val isOver9000: Int => Boolean = { x =>
  x > 9000
}
res0.map(isOver9000)

val stringIsLong = {
  s: String => s.length > 1000
}

Seq(5, 8, 11, 25).map({
  x => if (x % 2 == 0) {
    (x * x).toDouble
  } else {
    x.toDouble
  }
})
