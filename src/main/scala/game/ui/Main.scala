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

        val gameBoardCanvas = new CellCanvas
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

          content = List(
            playButton,
            resetButton,
            new Separator,
            genLabel,
            generations
          )
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
