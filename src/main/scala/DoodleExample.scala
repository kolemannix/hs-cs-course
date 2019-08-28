import doodle.core._
import doodle.core.Image._
import doodle.syntax._
import doodle.jvm.Java2DFrame._
import doodle.backend.StandardInterpreter._

// To use this example:
//
// 1. run `sbt`
// 2. run the `console` command within `sbt`
// 3. enter `Example.image.draw`
object DoodleExample extends App{
  val image = Image.circle(10).fillColor(Color.red) on Image.circle(20) on Image.circle(30)

  image.draw
}
