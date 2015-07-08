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
    gc.fill = Color.DarkGray

    val clickEventHandler = new EventHandler[MouseEvent] {
      override def handle(e: MouseEvent): Unit = {
        val x = e.x - (e.x % CellSize)
        val y = e.y - (e.y % CellSize)
        gc.fillRect(x, y, CellSize, CellSize)
      }
    }

    def clear(): Unit = gc.clearRect(0, 0, width.value, height.value)
    def enablePlotting() = addEventHandler(MouseEvent.MOUSE_CLICKED, clickEventHandler)
    def disablePlotting() = removeEventHandler(MouseEvent.MOUSE_CLICKED, clickEventHandler)
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
          // bind the canvas' dimensions to that of the parent's
          gameBoardCanvas.width <== width
          gameBoardCanvas.height <== height

          // set the gameboard canvas as a child of the group
          children = gameBoardCanvas
        }
      }
    }
  }
}
