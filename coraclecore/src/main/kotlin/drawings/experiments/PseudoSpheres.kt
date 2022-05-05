package drawings.experiments

import coracle.Drawing
import kotlin.math.cos
import kotlin.math.sin

/*
    Adapted from https://gist.github.com/volfegan/98044f8ebba0e728fabfcfc3ca2dea59
    See: https://www.dwitter.net/d/11965
 */
class PseudoSpheres: Drawing() {

    var t = 0f

    override fun setup() {
        size(450, 450)
        noStroke();
        fill(0xffffff, 0.10f)
    }

    override fun draw() {
        background(0x1d1d1d)
        t += 0.01f

        for (i in 2000 downTo 0){

            val q = (i * i).toFloat()
            val Q: Float = sin(q)

            val b = i % 6 + t //6 spheres
            //val b = i % 6 + t + i //torus

            val p = i + t
            val z: Float = 6 + cos(b) * 3 + cos(p) * Q
            val s = 99 / z / z //dot size

            circle(
                (width/2*(z+sin(b)*3+sin(p)*Q)/z).toInt(),
                (height/2 + width/2 * (cos(q)-cos(b+t))/z).toInt(),
                s.toInt()
            )
        }
    }
}