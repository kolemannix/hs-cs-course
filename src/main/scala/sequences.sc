// 1
val empty = Seq.empty[Int]
assert(empty.size == 0)

// 2
val a = Seq(42)

// 3
val xs = (1 to 50).toSeq
assert(xs.size == 50)

// 4
val b = xs ++ a

// 5
val z = xs :+ 100
val zz = 100 +: xs
z.last == 100
zz.head == 100

Seq(1, 1, 1).size == 3
Set(1, 1, 1).size == 1

xs.filter(x => x % 2 == 0)
xs.map(x => x * x)

