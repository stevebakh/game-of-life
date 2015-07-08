package game.ui

import javafx.event.EventHandler
import javafx.scene.input.MouseEvent

import scalafx.Includes._
import scalafx.scene.canvas.Canvas
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

  def plotCell(x: Long, y: Long) = {
    gc.fillRect(x * CellSize, y * CellSize, CellSize, CellSize)
    cells = cells + ((x, y))
  }

  def removeCell(x: Long, y: Long) = {
    gc.clearRect(x * CellSize, y * CellSize, CellSize, CellSize)
    cells = cells - ((x, y))
  }

  def enablePlotting() = addEventHandler(MouseEvent.MOUSE_CLICKED, clickEventHandler)
  def disablePlotting() = removeEventHandler(MouseEvent.MOUSE_CLICKED, clickEventHandler)

  val clickEventHandler = new EventHandler[MouseEvent] {
    override def handle(e: MouseEvent): Unit = {
      val x = ((e.x - (e.x % CellSize)) / CellSize).toLong
      val y = ((e.y - (e.y % CellSize)) / CellSize).toLong

      if (cells.contains((x, y)))
        removeCell(x, y)
      else
        plotCell(x, y)
    }
  }
}
