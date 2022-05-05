package drawings.experiments.images

import coracle.Colour
import coracle.Drawing
import coracle.Image
import coracle.Math.map

class ImagePixels: Drawing() {

    var testImage: Image? = null

    var pixelsLoaded = false

    var histogram = IntArray(256){ 0 }

    override fun setup() {
        size(450, 450)
        loadImage("salts_mill.png"){ image ->
            testImage = image
        }
        interactiveMode()
        stroke(0xffffff)
    }

    override fun draw() {
        testImage?.let{ image ->
            if(pixelsLoaded){
                //Draw histogram
                stroke(0xffffff)
                repeat(255) { index ->
                    val x = map(index.toFloat(), 0f, 255f, 0f, width.toFloat()).toInt()
                    val maxValue = histogram.maxOrNull() ?: 0
                    val v = map(histogram[index].toFloat(), 0f, maxValue.toFloat(), 0f, height.toFloat()).toInt()
                    line(x, height, x, height - v)
                }

                //Live pixel colour
                pixel(mouseX, mouseY)?.let{ pixel ->
                    val pixelColour = Colour(pixel)
                    fill(pixelColour)
                    circle(mouseX, mouseY, 20)
                }
            }else{
                loadPixels()
                repeat(width){ i ->
                    repeat(height){ j ->
                        pixel(i, j)?.let{ pixel ->
                            val brightness = Colour(pixel).brightness()
                            histogram[brightness]++
                        }
                    }
                }

                pixelsLoaded = true
            }
        }
    }
}