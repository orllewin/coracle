package drawings.experiments.rad

import coracle.Colour
import coracle.Drawing
import coracle.Image

class RadImageConverter: Drawing() {

    var sourceImage: Image? = null

    var colours = mutableListOf<Int>()
    var pixels = Array(256){ -1 }


    override fun setup() {
        size(256, 256)

        loadImage("001_16x16.png"){ image ->
           sourceImage = image
        }

        noStroke()
    }

    override fun draw() {
        sourceImage?.let{ image ->
            image(image)
            loadPixels()

            repeat(16){ y ->
                repeat(16){ x ->
                    val c = pixel(x, y)
                    c?.let{ colour ->
                        fill(colour)
                        rect(x * 16, y * 16, 16, 16)

                        if(!colours.contains(colour)){
                            colours.add(colour)
                        }

                        pixels[y * 16 + x] = colour
                    }

                }
            }

            val sb = StringBuilder()
            sb.append("16x16:")
            colours.forEach { colour ->
                sb.append(Colour(colour).toHexString())
            }

            sb.append(":")

            var activeColour = pixels.first()
            var activeCount = 1

            print("1D pixel array size: ${pixels.size}")
            repeat(256){ index ->
                val colour = pixels[index]
                //Verify we're indexing the 1D array correctly:
                val x = (index % 16) * 16
                val y = ((index / 16) % 16) * 16
                stroke(WHITE, 0.4f)
                fill(colour)
                circle(x + 8, y + 8, 8)

                //Check if colour has changed
                if(index > 0){
                    if(colour == activeColour){
                        //Increment
                        activeCount++
                    }else{
                        //Colour change
                        if(activeCount == 1){
                            sb.append(colours.indexOf(activeColour))
                        }else{
                            sb.append("|${colours.indexOf(activeColour)}.$activeCount|")
                        }

                        activeColour = colour
                        activeCount = 1
                    }
                }
            }

            var output = sb.toString().replace("||", "|").replace(":|", ":")
            if(output.endsWith("|")) output = output.dropLast(1)
            print(output)

            noLoop()

        }
    }
}