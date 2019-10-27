import com.sksamuel.scrimage.Image

object WorldTest extends App {

  case class State(score: Int)

  val flower = Image.fromResource("/image/flower.png")
  val flowerBuf = flower.toNewBufferedImage()

  val world = World[State](
    initial = State(3),
    onTick = identity,
    onKey = { (s, _) => s },
    onMouse = { (s, _, _, _) => s },
    stopWhen = { (s) => false },
    draw = { state => Scene(Seq(flowerBuf), Nil) }
  )

  world.init()

}
