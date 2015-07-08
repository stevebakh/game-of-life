package game.ui

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.scene.control._
import scalafx.scene.layout.BorderPane
import scalafx.scene.text.Text
import scalafx.scene.{Group, Scene}

object Main extends JFXApp {

  stage = new PrimaryStage {

    title = "Game of Life"
    width = 800
    height = 600
    resizable = false

    scene = new Scene {
      root = new BorderPane {

        val cellCanvas = new CellCanvas
        cellCanvas.enablePlotting()

        top = new ToolBar {
          val genLabel = new Label("generations:")
          val generations = new Text("0")

          val playButton = new ToggleButton("Run") {
            handleEvent(ActionEvent.Action) {
              e: ActionEvent =>
                if (selected.value) {
                  cellCanvas.disablePlotting()
                }
            }
          }

          val resetButton = new Button("Reset") {
            handleEvent(ActionEvent.Any) {
              e: ActionEvent =>
                if (playButton.selected.value)
                  playButton.fire()

                cellCanvas.clear()
                cellCanvas.enablePlotting()
                generations.text = "0"
            }
          }

          content = List(
            playButton,
            resetButton,
            new Separator,
            genLabel,
            generations
          )
        }

        center = new Group {
          cellCanvas.width <== width
          cellCanvas.height <== height
          children = cellCanvas
        }
      }
    }
  }
}
