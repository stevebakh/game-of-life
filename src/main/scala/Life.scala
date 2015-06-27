
class Life {

  private case class Position(x: Long, y: Long)

  case class Cell(x: Long, y: Long)

  private def candidatePositions(cells: Set[Cell]): Set[Position] =
    cells flatMap {
      cell =>
        for {
          x <- cell.x - 1 to cell.x + 1
          y <- cell.y - 1 to cell.y + 1
        } yield Position(x, y)
    }

  private def liveNeighbours(position: Position, cells: Set[Cell]): Int =
    (cells filter {
      cell =>
        !(cell.x == position.x && cell.y == position.y) &&
        cell.x >= position.x - 1 && cell.x <= position.x + 1 &&
        cell.y >= position.y - 1 && cell.y <= position.y + 1
    }).size

  def tick(cells: Set[Cell]): Set[Cell] =
    candidatePositions(cells) filter {
      pos =>
        val n = liveNeighbours(pos, cells)
        cells find (cell => cell.x == pos.x && cell.y == pos.y) match {
          case None => n == 3
          case Some(cell) => n >= 2 && n <= 3
        }
    } map (pos => Cell(pos.x, pos.y))
    
}
