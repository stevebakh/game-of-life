package game.ui

import scalafx.Includes._
import scalafx.event.Event
import scalafx.event.EventIncludes._
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.MouseEvent
import scalafx.scene.input.MouseEvent._
import scalafx.scene.paint.Color

class CellCanvas extends Canvas {

  private val CellSize = 10
  private val gc = graphicsContext2D
  private var cells: Set[(Long, Long)] = Set.empty
  gc.fill = Color.DarkGray

  def cellCoords: Set[(Long, Long)] = cells

  def clear(): Unit = {
    gc.clearRect(0, 0, width.value, height.value)
    cells = Set.empty
  }

  def plotCell(x: Long, y: Long): Unit = {
    gc.fillRect(x * CellSize, y * CellSize, CellSize, CellSize)
    cells = cells + ((x, y))
  }

  def removeCell(x: Long, y: Long): Unit = {
    gc.clearRect(x * CellSize, y * CellSize, CellSize, CellSize)
    cells = cells - ((x, y))
  }

  handleEvent(MouseEvent.Any) {
    e: MouseEvent => e.eventType match {
      case MouseClicked | MouseDragged =>
        val x = ((e.x - (e.x % CellSize)) / CellSize).toLong
        val y = ((e.y - (e.y % CellSize)) / CellSize).toLong
        plotCell(x, y)
      case _ => ()
    }
  }
}
