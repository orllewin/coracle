package coracle.shapes

import coracle.Easel

class Triangle(var a: Point, var b: Point, var c: Point){
    constructor(a1: Float, b1: Float, c1: Float, a2: Float, b2: Float, c2: Float, a3: Float, b3: Float, c3: Float): this(Point(a1, b1, c1), Point(a2, b2, c2), Point(a3, b3, c3))

    var colour = 0xffffff

    fun colour(colour: Int): Triangle{
        this.colour = colour
        return this
    }

    fun scale(amount: Float){
        a.x *= amount
        a.y *= amount
        b.x *= amount
        b.y *= amount
        c.x *= amount
        c.y *= amount
    }

    fun translate(x: Float, y: Float){
        a.x += x
        a.y += y
        b.x += x
        b.y += y
        c.x += x
        c.y += y
    }

    fun draw(){
        Easel.drawing?.triangle(this)
    }
}