
import org.scalatest.FunSpec
import org.scalatest.Matchers

/**
 * These tests refer to names of patterns defined on the
 * Wikipedia page for Conway's Game of Life.
 * See: https://en.wikipedia.org/wiki/Conway's_Game_of_Life#Examples_of_patterns
 */
class LifeSpec extends FunSpec with Matchers {

  val life = new Life()

  describe("For any given tick") {
    describe("when there are no living cells") {
      it("none will come to life") {
        life.tick(Set.empty) shouldBe empty
      }
    }

    describe("when only one cell is alive") {
      it("it will die and there will be no living cells") {
        val cells = Set(life.Cell(2, 4))
        life.tick(cells) shouldBe empty
      }
    }

    describe("when there are two living cells") {
      it("they will both die and there will be no living cells") {
        val cells = Set(life.Cell(2, 4), life.Cell(2, 5))
        life.tick(cells) shouldBe empty
      }
    }

    describe("when there are three living cells") {
      describe("adjacent to one another") {
        val cells = Set(life.Cell(2, 3), life.Cell(3, 3), life.Cell(4, 3))
        val result = life.tick(cells)

        it("two of the cells die and two new cells are born") {
          (cells diff result) should have size 2
        }

        it("there will still be a total of three cells") {
          result should have size 3
        }
      }
    }

    describe("still life patterns should not change") {
      describe("so when there are cells that form the 'boat' pattern") {
        it("none of the cells die and no new cells are born") {
          val boat = Set(
            life.Cell(2, 2), life.Cell(3, 2),
            life.Cell(2, 3), life.Cell(4, 3),
            life.Cell(3, 4))

          val result = life.tick(boat)
          boat shouldEqual result
        }
      }

      describe("and when there are cells that form the 'beehive' pattern") {
        it("none of the cells die and no new cells are born") {
          val beehive = Set(
            life.Cell(3, 2), life.Cell(4, 2),
            life.Cell(2, 3), life.Cell(5, 3),
            life.Cell(3, 4), life.Cell(4, 4))

          val result = life.tick(beehive)
          beehive shouldEqual result
        }
      }
    }
  }
}
