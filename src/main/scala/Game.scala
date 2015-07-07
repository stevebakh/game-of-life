import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{ScrollPane, ToggleButton, ToolBar}
import scalafx.scene.input.{ZoomEvent, MouseEvent}
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color
import scalafx.scene.{Group, Scene}

object Game extends JFXApp {

  class GameBoardCanvas extends Canvas {

    def show(): Unit = println("dimensions changed")
    width onChange show()
    height onChange show()

    val CellSize = 20
    val gc = graphicsContext2D
    gc.fill = Color.rgb(204, 120, 50)

    handleEvent(MouseEvent.MouseClicked) {
      e: MouseEvent =>
        val x = e.x - (e.x % CellSize)
        val y = e.y - (e.y % CellSize)
        gc.fillRect(x, y, CellSize, CellSize)
    }
  }

  stage = new PrimaryStage {
    title = "Game of Life"
    minWidth = 800
    minHeight = 600
    scene = new Scene {
      root = new BorderPane {
        top = new ToolBar {
          content = List(new ToggleButton("Run"))
        }

        center = new ScrollPane {
          style = "-fx-background: rgb(43, 43, 43); -fx-background-color: rgb(43, 43, 43)"
          hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
          vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER

          hvalue = 10
          vvalue = 10

          content = new Group {
            var gameBoardCanvas = new GameBoardCanvas

            // bind the canvas' dimensions to that of the parent's
            gameBoardCanvas.width <== width //* 2
            gameBoardCanvas.height <== height //* 2

            // set the gameboard canvas as a child of the group
            children = gameBoardCanvas

            handleEvent(ZoomEvent.ANY) {
              e: ZoomEvent =>
                if (!e.zoomFactor.isNaN) {
                  val scale = scaleX.value * e.zoomFactor
                  if (scale >= 0.1 && scale <= 1.0) {
                    scaleX = scale
                    scaleY = scale
                  }
                }
            }
          }
        }
      }
    }
  }
}
