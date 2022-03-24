import coracle.Drawing
import coracle.randomInt

class DemoDrawing: Drawing() {

    override fun setup() {
        size(300, 300)
        stroke(0x000000)
    }

    override fun draw() {
        background(0xffffff)
        line(randomInt(width), 0, randomInt(width), height)
        circle(randomInt(width), randomInt(height), randomInt(30))

        repeat(100){
            point(randomInt(width), randomInt(height))
        }
    }
}