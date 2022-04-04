package examples.algorithms

import coracle.*
import coracle.shapes.Point
import coracle.shapes.Rect
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class QuadtreeDrawing: Drawing() {

    lateinit var quadtree: Node
    var frame = 0

    override fun setup() {
        size(600, 600)

        quadtree = Node(Rect(width, height))
    }

    override fun draw() {
        background(DEFAULT_BACKGROUND)
        val c = coordWithinCircle()
        quadtree.addPoint(Point(c.x, c.y)).draw()

        frame++
        if(frame > 1000){
            quadtree.reset()
            frame = 0
        }
    }

    private fun coordWithinCircle(): Coord {
        val a = random(0f, 1f) * TWO_PI
        val r = 200f * sqrt(random(0f, 1f))
        val x = r * cos(a)
        val y = r * sin(a)
        return Coord(width/2 + x.toFloat(), height/2 + y.toFloat())
    }

    inner class Node(val rect: Rect){
        var capacity = 5
        var leaf = true
        var points = mutableListOf<Point>()
        var nodes = arrayOfNulls<Node?>(4)

        val nodeWidth = rect.width/2
        val nodeHeight = rect.height/2

        fun draw(){
            noFill()
            stroke(0x1d1d1d, 0.25f)
            rect(rect)

            noStroke()
            fill(0xff00cc, 0.12f)
            points.forEach { point ->
                fill(DEFAULT_FOREGROUND, 0.1f)
                circle(point.x, point.y, 12)

                fill(0x1d1d1d, 0.5f)
                circle(point.x, point.y, 2)
            }

            if(!leaf){
                nodes.forEach { node ->
                    node?.draw()
                }
            }
        }

        fun reset(){
            nodes = arrayOfNulls<Node?>(4)
            points.clear()
            leaf = true
        }

        fun addPoint(point: Point): Node{
            if(rect contains point){
                when {
                    leaf && points.isEmpty() -> points.add(point)
                    leaf && points.size < capacity - 1 -> {
                        //For circle packing add collision check here, in the example just add:
                        points.add(point)
                    }
                    leaf -> split(point)
                    else -> findNode(point)
                }
            }else{
                throw Exception("Point ${point.x}.${point.y} is not within rect ${rect.x}.${rect.y} ${rect.width}x${rect.height}")
            }
            return this
        }

        private fun split(point: Point){
            leaf = false

            nodes[0] = Node(Rect(rect.x, rect.y, nodeWidth, nodeHeight))//top left
            nodes[1] = Node(Rect(rect.x + nodeWidth, rect.y, nodeWidth, nodeHeight))//top right
            nodes[2] = Node(Rect(rect.x, rect.y + nodeHeight, nodeWidth, nodeHeight))//bottom left
            nodes[3] = Node(Rect(rect.x + nodeWidth, rect.y + nodeHeight, nodeWidth, nodeHeight))//bottom right

            points.forEach(this@Node::findNode)
            points.clear()
            addPoint(point)
        }

        private fun findNode(point: Point){
            when {
                point.y < rect.y + nodeHeight ->{
                    when {
                        point.x < rect.x + nodeWidth -> nodes[0]?.addPoint(point)//top left
                        else -> nodes[1]?.addPoint(point)//top right
                    }
                }
                else -> {
                    when {
                        point.x < rect.x + nodeWidth -> nodes[2]?.addPoint(point)//bottom left
                        else -> nodes[3]?.addPoint(point)//bottom right
                    }
                }
            }
        }
    }
}