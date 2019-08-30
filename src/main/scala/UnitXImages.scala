import com.sksamuel.scrimage.{ Image, Pixel }

import Img._

object UnitXImages extends App {

  // Images to play with
  val yoda = Image.fromResource("/image/yoda.png")
  val flower = Image.fromResource("/image/flower.png")
  val gandalf = Image.fromResource("/image/gandalf.png")
  val ramp = Image.fromResource("/image/ramp.png")

  // draw(ramp)
//    draw(gandalf)
//  draw(flower)
  //  draw(yoda)

  // Exercises 1-5 can be done with an image of your choice from the above options,
  // or, use an image of your own!

  // Exercise 1 - Maximize all the green values, leaving everything else the same
  // Name your resulting image exercise1
  val exercise1 = transformImage(flower, p => Pixel(p.red, 255, p.green, p.alpha))
  // draw(exercise1)

  // Exercise 2 - Convert an image to grayscale using the Average method
  // Name your resulting image exercise2
  val exercise2 = transformImage(gandalf, { p =>
    val avg = (p.red + p.green + p.blue) / 3
    Pixel(avg, avg, avg, p.alpha)
  })
  draw(exercise2)

  // Exercise 3 - Convert an image to grayscale using the Luminance method
  // Name your resulting image exercise3
  val lumRed = 0.30
  val lumGreen = 0.59
  val lumBlue = 0.11
  val exercise3 = transformImage(flower, { p =>
    val avgLum = (lumRed * p.red) + (lumGreen * p.green) + (lumBlue * p.blue)
    Pixel(avgLum.toInt, avgLum.toInt, avgLum.toInt, p.alpha)
  })
  // draw(exercise3)

  // Think about the similarities between exercise 2 and exercise 3.
  // - Are they really the same algorithm?
  // - Is luminance a more general form of the average method?
  // - Hint: What is a 'weighted average'?
  // Write a function capable of transforming an image using both methods

  // Exercise 4 - Decoding hidden images!
  // There is a hidden image inside one of the color channels of puzzle_one.
  // Can you decode the image? What is puzzle_one a picture of?
  lazy val puzzleOne = Image.fromResource("/image/puzzle_one.png")

  def decodeOne(p: Pixel): Pixel = {
    val redBoosted = p.red * 10
    Pixel(r = redBoosted, g = redBoosted, b = redBoosted, p.alpha)
  }
  val exercise4 = transformImage(puzzleOne, decodeOne)
  draw(puzzleOne)
  draw(exercise4)

  // Exercise 5
  // There is a hidden image in puzzle_two. Can you write a transformation to
  // bring it out? What is it a picture of?
  val puzzleTwo = Image.fromResource("/image/puzzle_two.png")
  def decodeTwo(p: Pixel): Pixel = {
    Pixel(0, p.green * 20, p.blue * 20, p.alpha)
  }
  val exercise5 = transformImage(puzzleTwo, decodeTwo)
  //  draw(puzzleTwo)
  //  draw(exercise5)

  // Exercise 6
  val puzzleThree = Image.fromResource("/image/puzzle_three.png")

  def decodeThree(p: Pixel): Pixel = {
    val blueValue =
      if (p.blue < 16)
        p.blue * 16.0
      else
        p.blue
    Pixel(0, 0, blueValue.toInt, p.alpha)
  }
  val exercise6 = transformImage(puzzleThree, decodeThree)
  val reds = transformImage(puzzleThree, { p => Pixel(p.red, 0, 0, p.alpha)})
  val greens = transformImage(puzzleThree, { p => Pixel(0, p.green, 0, p.alpha)})
  val blues = transformImage(puzzleThree, { p => Pixel(0, 0, p.blue, p.alpha)})
//  draw(reds)
//  draw(greens)
//  draw(blues)
//  draw(puzzleThree)
//  draw(exercise6)
}
