package chess

import chess.game.Game
import chess.game.model.Movement.asArrayOfInt
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class RookSpec extends AnyFlatSpec with should.Matchers {

  val game: Game = Game()

  "A Rook" should "not be moved from a1 to a1" in {
    game.performMove(asArrayOfInt("a1a1")) shouldBe false
  }

  it should "not be moved from a1 to a2" in {
    game.performMove(asArrayOfInt("a1a2")) shouldBe false
  }

  it should "be moved from a1 to a2 after pawn is moved first" in {
    game.performMove(asArrayOfInt("a2a3")) shouldBe true
    game.performMove(asArrayOfInt("a7a6")) shouldBe true
    game.performMove(asArrayOfInt("a1a2")) shouldBe true
  }

  it must "not be moved diagonally" in {
    game.performMove(asArrayOfInt("a2b3")) shouldBe false
  }
}
