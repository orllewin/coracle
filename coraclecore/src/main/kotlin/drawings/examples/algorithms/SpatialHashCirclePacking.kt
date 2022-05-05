package drawings.examples.algorithms

import coracle.*
import coracle.shapes.Circle
import coracle.shapes.Point
import coracle.shapes.Rect
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class SpatialHashCirclePacking: Drawing() {

    private lateinit var spatialHash: SpatialHash
    private var maxRad = randomInt(8, 20)

    private val backgroundColour = 0xF2EDF2
    private val gridColour = 0xC3BDD4
    private val defaultColour = 0xA87CAA
    private val boundaryColour = 0xC57596

    override fun setup() {
        size(600, 600)

        val columns = randomInt(3, 8)
        val rows = randomInt(3, 8)
        spatialHash = SpatialHash(columns, rows)
        print("Columns: $columns Rows: $rows Maximum radius: $maxRad")
    }

    override fun draw() {
        background(backgroundColour)

        spatialHash
            .addMultiple(20)
            .grow()
            .checkCells()
            .draw()
    }

    private fun coordWithinCircle(): Point {
        val a = random(0f, 1f) * TWO_PI
        val r = ((width/2) * 0.8) * sqrt(random(0f, 1f))
        val x = r * cos(a)
        val y = r * sin(a)
        return Point(width/2 + x.toFloat(), height/2 + y.toFloat())
    }

    inner class GrowingCircle(x: Float, y: Float, radius: Int): Circle(x, y, radius){
        var growing = true
        var colour = defaultColour

        fun drawGrowingCircle(){
            noStroke()
            fill(colour, 1f)
            circle(x, y, r)
        }
    }

    inner class SpatialHash(private val columns: Int, private val rows: Int){

        private val cellPopulations = HashMap<Int, MutableList<GrowingCircle>>()
        private val cellNeighbours =  HashMap<Int, MutableList<GrowingCircle>>()
        private val cellsRects =      HashMap<Int, Rect>()
        private val pendingUpdates = mutableListOf<Pair<Int, GrowingCircle>>()
        private val cellWidth: Int
        private val cellHeight: Int

        init {
            repeat(columns * rows){ index ->
                cellPopulations[index] = mutableListOf()
                cellNeighbours[index] = mutableListOf()
            }

            cellWidth = width/columns
            cellHeight = height/rows

            var index = -1
            repeat(rows){ row ->
                repeat(columns){ column ->
                    index++
                    val rect = Rect(column * cellWidth, row * cellHeight, cellWidth, cellHeight)
                    cellPopulations[index] = mutableListOf()
                    cellsRects[index] = rect
                }
            }
        }

        fun addMultiple(count: Int): SpatialHash {
            repeat(count){
                val randCoord = coordWithinCircle()
                add(GrowingCircle(randCoord.x, randCoord.y, 1))
            }
            return this
        }

        fun add(c: GrowingCircle): SpatialHash {
            val index = getIndexHash(c.x.toInt(), c.y.toInt())
            var collision = false

            val population = cellPopulations[index]
            population?.forEach { e ->
                if(collision(c, e)) collision = true
            }

            val neighbours = cellNeighbours[index]
            neighbours?.forEach { e ->
                if(collision(c, e)) collision = true
            }

            if(!collision) population?.add(c)

            return this
        }

        fun grow(): SpatialHash {
            cellPopulations.forEach { cellCollection ->
                val circles = cellCollection.value
                val neighbours = cellNeighbours[cellCollection.key]
                circles.forEach { c ->
                    if(c.growing && c.r < maxRad){
                        c.r++
                        if(collision(c, circles) || collision(c, neighbours ?: emptyList())){
                            c.r--
                            c.growing = false
                        }
                    }else{
                        c.growing = false
                    }
                }
            }

            return this
        }

        private fun collision(c: Circle, circles: List<Circle>): Boolean{
            circles.forEach { o ->
                if(c != o){
                    if(collision(c, o)) return true
                }
            }

            return false
        }

        private fun collision(a: Circle, b: Circle): Boolean{
            return Vector(a.x, a.y).distance(Vector(b.x, b.y)) <= a.r + b.r
        }

        private fun getIndexHash(x: Int, y: Int): Int {
            val col = (x * columns / (width + 1))
            val row = (y * rows / (height + 1))
            return row * columns + col
        }

        /*
            As the circles grow check if they encroach on neighbouring cells
         */
        fun checkCells(): SpatialHash {
            pendingUpdates.clear()
            cellPopulations.forEach { cellCollection ->
                val key = cellCollection.key
                val circles = cellCollection.value
                val nativeRect = cellsRects[key]
                circles.forEach { c ->
                    if(!fullyEnclosed(c, nativeRect!!)){
                        c.colour = boundaryColour
                        addToNeighbouringCells(key, c)
                    }
                }
            }

            pendingUpdates.forEach { u ->
                val key = u.first
                if(cellPopulations.containsKey(key)){
                    val neighbours = cellNeighbours[key]
                    if(!neighbours!!.contains(u.second)) neighbours.add(u.second)
                }
            }

            return this
        }

        private fun fullyEnclosed(c: Circle, r: Rect): Boolean =
            c.x - c.r > r.x && c.x + c.r < r.x + r.width && c.y - c.r > r.y && c.y + c.r < r.y + r.height

        private fun addToNeighbouringCells(nativeKey: Int, c: GrowingCircle){
            val nativeRect = cellsRects[nativeKey]!!
            if(c.x - c.r <= nativeRect.x){
                val leftIndex = getIndexHash((c.x - c.r).toInt(), c.y.toInt())
                pendingUpdates.add(Pair(leftIndex, c))

                when {
                    c.y - c.r <= nativeRect.y -> {
                        val aboveLeftIndex = getIndexHash((c.x - c.r).toInt(), (c.y - c.r).toInt())
                        pendingUpdates.add(Pair(aboveLeftIndex, c))
                    }
                    c.y + c.r >= nativeRect.y + nativeRect.height -> {
                        val belowLeftIndex = getIndexHash((c.x - c.r).toInt(), (c.y + c.r).toInt())
                        pendingUpdates.add(Pair(belowLeftIndex, c))
                    }
                }
            }
            if(c.x + c.r >= nativeRect.x + nativeRect.width){
                val rightIndex = getIndexHash((c.x + c.r).toInt(), c.y.toInt())
                pendingUpdates.add(Pair(rightIndex, c))

                when {
                    c.y - c.r <= nativeRect.y -> {
                        val aboveRightIndex = getIndexHash((c.x + c.r).toInt(), (c.y - c.r).toInt())
                        pendingUpdates.add(Pair(aboveRightIndex, c))
                    }
                    c.y + c.r >= nativeRect.y + nativeRect.height -> {
                        val belowRightIndex = getIndexHash((c.x + c.r).toInt(), (c.y + c.r).toInt())
                        pendingUpdates.add(Pair(belowRightIndex, c))
                    }
                }
            }
            if(c.y - c.r <= nativeRect.y){
                val aboveIndex = getIndexHash(c.x.toInt(), (c.y - c.r).toInt())//Above
                pendingUpdates.add(Pair(aboveIndex, c))
            }
            if(c.y + c.r >= nativeRect.y + nativeRect.height){
                val belowIndex = getIndexHash(c.x.toInt(), (c.y + c.r).toInt())//Below
                pendingUpdates.add(Pair(belowIndex, c))
            }
        }

        fun draw(){
            cellPopulations.forEach { cellCollection ->
                cellCollection.value.forEach { c -> c.drawGrowingCircle() }
            }

            stroke(gridColour, 0.35f)
            noFill()
            cellsRects.forEach { rect ->
                rect(rect.value)
            }
        }
    }
}