package game.logic

object Life {

  case class Cell(x: Long, y: Long)

  private def candidateCells(cells: Set[Cell]): Set[Cell] =
    cells flatMap {
      cell =>
        for {
          x <- cell.x - 1 to cell.x + 1
          y <- cell.y - 1 to cell.y + 1
        } yield Cell(x, y)
    }

  private def liveNeighbours(candidate: Cell, cells: Set[Cell]): Set[Cell] =
    cells filter {
      cell =>
        cell != candidate &&
        math.abs(cell.x - candidate.x) <= 1 &&
        math.abs(cell.y - candidate.y) <= 1
    }

  def evolve(cells: Set[Cell]): Set[Cell] =
    candidateCells(cells) filter {
      candidate =>
        val n = liveNeighbours(candidate, cells).size
        (cells contains candidate) && n == 2 || n == 3
    }
}
