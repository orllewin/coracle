package drawings.experiments.images

import coracle.Drawing
import coracle.Image

class BasicImage: Drawing() {

    var testImage: Image? = null

    var x = 0

    override fun setup() {
        size(450, 550)

        x = width

         loadImage("salts_mill.png"){ image ->
            testImage = image
        }
    }

    override fun draw() {
        testImage?.let{ image ->
            image(image, x, 0)
            x--

            image(image, 0, 275, width, height/2)

            if(x < 0){
                noLoop()
            }
        }


    }
}