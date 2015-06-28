
class Life {

  case class Cell(x: Long, y: Long)

  private def candidatePositions(cells: Set[Cell]): Set[Cell] =
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
        !(cell.x == candidate.x && cell.y == candidate.y) &&
        cell.x >= candidate.x - 1 && cell.x <= candidate.x + 1 &&
        cell.y >= candidate.y - 1 && cell.y <= candidate.y + 1
    }

  def tick(cells: Set[Cell]): Set[Cell] =
    candidatePositions(cells) filter {
      candidate =>
        val n = liveNeighbours(candidate, cells).size
        cells find (c => c.x == candidate.x && c.y == candidate.y) match {
          case None => n == 3
          case Some(cell) => n >= 2 && n <= 3
        }
    }
}
