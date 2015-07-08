package game.ui

import game.logic.{Cell => GameCell}
import game.logic.Life
import scalafx.Includes._
import scalafx.animation.KeyFrame
import scalafx.animation.Timeline
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.scene.control._
import scalafx.scene.layout.BorderPane
import scalafx.scene.text.Text
import scalafx.scene.{Group, Scene}
import scalafx.util.Duration

object Main extends JFXApp {

  val cellCanvas = new CellCanvas
  cellCanvas.enablePlotting()

  stage = new PrimaryStage {

    title = "Game of Life"
    width = 800
    height = 600
    resizable = false

    scene = new Scene {
      root = new BorderPane {

        top = new ToolBar {
          val generation = new Text("0")
          val population = new Text("0")

          val playButton = new ToggleButton("Run") {
            handleEvent(ActionEvent.Action) {
              e: ActionEvent =>
                if (!selected.value) {
                  timeline.pause()
                } else {
                  cellCanvas.disablePlotting()
                  if (cellCanvas.cellCoords.nonEmpty) {
                    timeline.play()
                  }
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
                generation.text = "0"
                population.text = "0"
                timeline.stop()
            }
          }

          content = List(
            playButton,
            resetButton, 
            new Separator, 
            new Label("generation:"), 
            generation,
            new Label("population:"),
            population)

          val life = new Life

          var timeline = new Timeline {
            cycleCount = Timeline.Indefinite
            keyFrames = KeyFrame(Duration(200), onFinished = (e: ActionEvent) => {
              var seed = cellCanvas.cellCoords.map(c => new GameCell(c._1, c._2))
              cellCanvas.clear()
              val evolved = life.evolve(seed)
              evolved.foreach(cell => cellCanvas.plotCell(cell.x, cell.y))
              population.text = evolved.size.toString
              generation.text = (generation.text.value.toLong + 1).toString
            })
          }
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
