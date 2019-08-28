import java.awt.Graphics
import java.net.URL

import com.sksamuel.scrimage.{ Image, Pixel }
import javax.swing.{ JPanel, WindowConstants }

object Img {
  def transformImage(i: Image, fn: Pixel => Pixel): Image = {
    i.map({ (_, _, pixel) =>
      fn(pixel)
    })
  }

  def loadImageUrl(url: String): Image = {
    val inputStream = new URL("https://dl.dropboxusercontent.com/s/m5qqmupg0h6w97k/yoda.bmp?dl=0").openStream()
    Image.fromStream(inputStream)
  }

  def draw(image: Image, title: String = "Image"): Unit = {
    val swingFrame = new javax.swing.JFrame(title)

    val bufImg = image.toNewBufferedImage()
    val panel = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        g.drawImage(bufImg, 0, 0, null)
      }
    }
    swingFrame.add(panel)

    swingFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
    swingFrame.setSize(image.width, image.height)
    swingFrame.setVisible(true)
  }

}
