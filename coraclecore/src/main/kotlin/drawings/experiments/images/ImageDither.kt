package drawings.experiments.images

import coracle.Drawing
import coracle.Image

class ImageDither: Drawing() {

    var testImage: Image? = null
    var threshold = 128

    override fun setup() {
        size(550, 414)
        loadImage("salts_mill.png"){ image ->
            print("Image loaded: ${image?.path}")
            testImage = image
        }
    }

    override fun draw() {
        testImage?.let{ image ->
            image(image, 0, 0)
            loadPixels()

            val errors = Array(width) { IntArray(height) }

            for (y in 0 until height - 1) {
                for (x in 1 until width - 1) {
                    val gray = (pixel(x, y)?.shr(16) ?: 0) and 0xFF
                    var error: Int
                    when {
                        gray + errors[x][y] < threshold -> {
                            error = gray + errors[x][y]
                            stroke(BLACK)
                            point(x, y)
                        }
                        else -> {
                            error = gray + errors[x][y] - 255
                            stroke(WHITE)
                            point(x, y)
                        }
                    }
                    errors[x + 1][y] += 7 * error / 16
                    errors[x - 1][y + 1] += 3 * error / 16
                    errors[x][y + 1] += 5 * error / 16
                    errors[x + 1][y + 1] += 1 * error / 16
                }
            }
            noLoop()
        }
    }
}