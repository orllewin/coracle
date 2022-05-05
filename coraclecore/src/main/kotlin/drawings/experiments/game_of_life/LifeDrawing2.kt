package drawings.experiments.game_of_life

import coracle.Drawing
import coracle.random

/*
    https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
 */
class LifeDrawing2: Drawing() {
    val neighbourXIndexes = arrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
    val neighbourYIndexes = arrayOf(-1, -1, -1, 0, 0, 1, 1, 1)
    val cellsArray = Array(50 * 50) { Cell() }

    override fun setup() {
        size(500, 500)
        translate(5, 5)
        cellsArray.forEachIndexed { index, cell ->
            cell.x = index % 50
            cell.y = (index / 50) % 50
            cell.alive = random(100) < 10
        }
    }

    override fun draw() {
        background(0xeeeae4)
        fill(0xebb9ce)
        noStroke()

        cellsArray.forEach { cell ->
            cell.updateNext()
            cell.draw()
        }

        cellsArray.forEach { cell -> cell.alive = cell.pendingAlive }
    }

    inner class Cell{

        var alive: Boolean = false
        var pendingAlive: Boolean = false
        var x: Int = -1
        var y: Int = -1

        private var aliveNeighbours = 0

        fun updateNext(): Cell {

            aliveNeighbours = 0
            repeat(8){ index ->
                checkNeighbour(x + neighbourXIndexes[index], y + neighbourYIndexes[index])
            }

            when {
                aliveNeighbours < 2 -> pendingAlive = false
                aliveNeighbours > 3 -> pendingAlive = false
                alive && (aliveNeighbours == 2 || aliveNeighbours ==3) -> pendingAlive = alive//No change
                aliveNeighbours == 3 && !alive -> pendingAlive = true
            }

            return this
        }

        private fun checkNeighbour(x: Int, y: Int){
            if( x < 0 || x >= 50 || y < 0 || y >= 50) return
            if(cellsArray[y * 50 + x].alive) aliveNeighbours++
        }

        fun draw(){ if(alive) circle(x * 10, y * 10, 5) }
    }
}