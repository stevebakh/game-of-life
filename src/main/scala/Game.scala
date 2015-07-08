import javafx.event.EventHandler
import javafx.scene.input.MouseEvent

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.scene.canvas.Canvas
import scalafx.scene.control._
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color
import scalafx.scene.text.Text
import scalafx.scene.{Group, Scene}

object Game extends JFXApp {

  class GameBoardCanvas extends Canvas {

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

  stage = new PrimaryStage {

    title = "Game of Life"
    width = 800
    height = 600
    resizable = false

    scene = new Scene {
      root = new BorderPane {

        val gameBoardCanvas = new GameBoardCanvas
        gameBoardCanvas.enablePlotting()

        top = new ToolBar {
          val genLabel = new Label("generations:")
          val generations = new Text("0")

          val playButton = new ToggleButton("Run") {
            handleEvent(ActionEvent.Action) {
              e: ActionEvent =>
                if (selected.value) {
                  gameBoardCanvas.disablePlotting()
                }
            }
          }

          val resetButton = new Button("Reset") {
            handleEvent(ActionEvent.Any) {
              e: ActionEvent =>
                if (playButton.selected.value)
                  playButton.fire()

                gameBoardCanvas.clear()
                gameBoardCanvas.enablePlotting()
                generations.text = "0"
            }
          }

          val sep = new Separator

          content = List(playButton, resetButton, sep, genLabel, generations)
        }

        center = new Group {
          gameBoardCanvas.width <== width
          gameBoardCanvas.height <== height

          children = gameBoardCanvas
        }
      }
    }
  }
}
