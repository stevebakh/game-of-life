package game.logic

import org.scalatest.FunSpec
import org.scalatest.Matchers

/**
 * These tests refer to names of patterns defined on the
 * Wikipedia page for Conway's Game of Life.
 * See: https://en.wikipedia.org/wiki/Conway's_Game_of_Life#Examples_of_patterns
 */
class LifeSpec extends FunSpec with Matchers {

  val life = new Life()

  describe("For any single tick") {
    describe("when there are no living cells") {
      it("none will come to life") {
        life.evolve(Set.empty) shouldBe empty
      }
    }

    describe("when only one cell is alive") {
      it("it will die and there will be no living cells") {
        val cells = Set(Cell(2, 4))
        life.evolve(cells) shouldBe empty
      }
    }

    describe("when there are two living cells") {
      it("they will both die and there will be no living cells") {
        val cells = Set(Cell(2, 4), Cell(2, 5))
        life.evolve(cells) shouldBe empty
      }
    }

    describe("when a candidate position has three living neighbours") {
      it("a new cell will come to life at that position") {
        val cells = Set(Cell(2, 1), Cell(1, 2), Cell(2, 2))
        val tick1 = life.evolve(cells)
        tick1 should contain (Cell(1, 1))
      }
    }

    describe("still life patterns should not change") {
      describe("so when there are cells that form the 'boat' pattern") {
        it("none of the cells die and no new cells are born") {
          val boat = Set(
            Cell(2, 2), Cell(3, 2),
            Cell(2, 3), Cell(4, 3),
            Cell(3, 4))

          val result = life.evolve(boat)
          boat shouldEqual result
        }
      }

      describe("likewise, when there are cells that form the 'beehive' pattern") {
        it("none of the cells die and no new cells are born") {
          val beehive = Set(
            Cell(3, 2), Cell(4, 2),
            Cell(2, 3), Cell(5, 3),
            Cell(3, 4), Cell(4, 4))

          val result = life.evolve(beehive)
          beehive shouldEqual result
        }
      }
    }
  }

  describe("When multiple ticks occur") {
    describe("some patterns will oscillate between several states") {
      describe("so when the 'blinker' pattern emerges") {
        val start = Set(Cell(2, 3), Cell(3, 3), Cell(4, 3))
        val expected = Set(Cell(3, 2), Cell(3, 3), Cell(3, 4))
        val tick1 = life.evolve(start)
        val tick2 = life.evolve(tick1)

        it("the first tick will produce three live cells") {
          tick1 should have size 3
        }

        it("where two of the cells die and two new cells are born") {
          (start diff tick1) should have size 2
        }

        it("and the pattern matches the expected oscillation") {
          tick1 shouldEqual expected
        }

        it("then the second tick returns cells in their original positions") {
          tick2 shouldEqual start
        }
      }

      describe("or if the 'toad' pattern emerges") {
        val start = Set(
          Cell(3, 3), Cell(4, 3), Cell(5, 3),
          Cell(2, 4), Cell(3, 4), Cell(4, 4))

        val expected = Set(
          Cell(4, 2), Cell(2, 3), Cell(5, 3),
          Cell(2, 4), Cell(5, 4), Cell(3, 5))

        val tick1 = life.evolve(start)
        val tick2 = life.evolve(tick1)

        it("the first tick will produce six live cells") {
          tick1 should have size 6
        }

        it("and the pattern matches the expected oscillation") {
          tick1 shouldEqual expected
        }

        it("then the second tick returns cells in their original positions") {
          tick2 shouldEqual start
        }
      }
    }
  }
}