
case class Cell(x: Long, y: Long)

class Life {

  private def candidatePositions(cells: Set[Cell]): Set[(Long, Long)] = 
    cells flatMap {
      cell => 
        for {
          x <- cell.x - 1 to cell.x + 1
          y <- cell.y - 1 to cell.y + 1
        } yield (x, y)
    }

  private def liveNeighbours(pos: (Long, Long), cells: Set[Cell]): Int = 
    (cells filter {
      cell =>
        !(cell.x == pos._1 && cell.y == pos._2) &&
        cell.x >= pos._1 - 1 && cell.x <= pos._1 + 1 &&
        cell.y >= pos._2 - 1 && cell.y <= pos._2 + 1
    }).size
  
  def tick(cells: Set[Cell]): Set[Cell] = 
    candidatePositions(cells) filter {
      pos => 
        val n = liveNeighbours(pos, cells)
        cells find (cell => cell.x == pos._1 && cell.y == pos._2) match {
          case None => n == 3
          case Some(cell) => n >= 2 && n <= 3
        }
    } map (pos => Cell(pos._1, pos._2))

}

