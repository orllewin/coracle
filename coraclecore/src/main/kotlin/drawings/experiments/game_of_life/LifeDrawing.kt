package drawings.experiments.game_of_life

import coracle.Drawing
import coracle.random

/*
    https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
 */
class LifeDrawing: Drawing() {

    val diam = 500
    val cells = mutableListOf<List<Cell>>()

    override fun setup() {
        size(diam, diam)

        repeat(50){ yIndex ->
            val row = mutableListOf<Cell>()
            repeat(50){ xIndex ->
                val alive = random(100) < 10
                row.add(Cell(alive, xIndex, yIndex))
            }
            cells.add(row)
        }

        repeat(50){ yIndex ->
            repeat(50){ xIndex ->
                cells[yIndex][xIndex].findNeighbours()
            }
        }

        translate(10, 10)

    }

    override fun draw() {
        background(0xb7ebd4)
        fill(0x9b42f5)
        noStroke()
        repeat(50){ yIndex ->
            val row = cells[yIndex]
            repeat(50){ xIndex ->
                val cell = row[xIndex]
                if(frame % 3 == 0)cell.update()
                cell.draw()
            }
        }
    }

    inner class Cell(
        private var alive: Boolean,
        private val xIndex: Int,
        private val yIndex: Int){

        private val neighbours = mutableListOf<Cell?>()

        fun update(): Cell {
            var aliveNeighbours = 0
            neighbours.forEach { neighbour ->
                neighbour?.let{
                    if(neighbour.alive) aliveNeighbours++
                }
            }

            //Any live cell with fewer than two live neighbours dies
            //Any live cell with more than three live neighbours dies
            //Any live cell with two or three live neighbours lives, unchanged, to the next generation
            //Any dead cell with exactly three live neighbours will come to life
            when {
                aliveNeighbours < 2 -> alive = false
                aliveNeighbours > 3 -> alive = false
                alive && (aliveNeighbours == 2 || aliveNeighbours ==3) -> Unit//NOOP
                aliveNeighbours == 3 && !alive -> alive = true
            }

            return this
        }

        fun draw(){
            when {
                alive -> square(xIndex * 10, yIndex * 10, 20)
            }
        }

        fun findNeighbours(){
            val topLeft: Cell? = when {
                yIndex > 0 && xIndex > 0 -> cells[yIndex - 1][xIndex -1]
                else -> null
            }
            neighbours.add(topLeft)
            val topMiddle: Cell? = when {
                yIndex > 0 -> cells[yIndex-1][xIndex]
                else -> null
            }
            neighbours.add(topMiddle)
            val topRight: Cell? = when {
                yIndex > 0 && xIndex < 49 -> cells[yIndex - 1][xIndex + 1]
                else -> null
            }
            neighbours.add(topRight)
            val left: Cell? = when{
                xIndex > 0 -> cells[yIndex][xIndex - 1]
                else -> null
            }
            neighbours.add(left)
            val right: Cell? = when{
                xIndex < 49 -> cells[yIndex][xIndex + 1]
                else -> null
            }
            neighbours.add(right)
            val bottomLeft = when{
                xIndex > 0 && yIndex < 49 -> cells[yIndex + 1][xIndex - 1]
                else -> null
            }
            neighbours.add(bottomLeft)
            val bottomMiddle = when{
                yIndex < 49 -> cells[yIndex + 1][xIndex]
                else -> null
            }
            neighbours.add(bottomMiddle)
            val bottomRight = when{
                xIndex < 49 && yIndex < 49 -> cells[yIndex + 1][xIndex + 1]
                else -> null
            }
            neighbours.add(bottomRight)
        }
    }
}