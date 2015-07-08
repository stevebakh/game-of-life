package game.ui

import javafx.event.EventHandler
import javafx.scene.input.MouseEvent

import scalafx.Includes._
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

class CellCanvas extends Canvas {

  val CellSize = 10
  val gc = graphicsContext2D
  var cells: Set[(Long, Long)] = Set.empty
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
      val x = e.x - (e.x % CellSize)
      val y = e.y - (e.y % CellSize)
      val coords = (x.toLong / CellSize, y.toLong / CellSize)
      if (cells.contains(coords))
        removeCell(coords._1, coords._2)
      else
        plotCell(coords._1, coords._2)
    }
  }
}