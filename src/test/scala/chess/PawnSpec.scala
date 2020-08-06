package chess

import chess.game.Game
import chess.game.model.Movement.asArrayOfInt
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class PawnSpec extends AnyFlatSpec with should.Matchers {

  val game: Game = Game()

  "A Pawn" should "be moved 1 raw forward only" in {
    game.performMove(asArrayOfInt("d2d3")) shouldBe true
    game.performMove(asArrayOfInt("c7c6")) shouldBe true
  }

  it should "be also be moved 2 rows forward only" in {
    game.performMove(asArrayOfInt("e2e4")) shouldBe true
    game.performMove(asArrayOfInt("b7b5")) shouldBe true
  }

  it should "not be moved 3 rows" in {
    game.performMove(asArrayOfInt("a2a5")) shouldBe false
  }

  it should "cannot move 2 rows forward if it was not moved before" in {
    game.performMove(asArrayOfInt("c6c4")) shouldBe false
  }

  it should "not be moved backward" in {
    game.performMove(asArrayOfInt("e4e3")) shouldBe false
    game.performMove(asArrayOfInt("e4c3")) shouldBe false
  }

  it should "capture only diagonally" in {
    game.performMove(asArrayOfInt("f2f4")) shouldBe true
    game.performMove(asArrayOfInt("f7f5")) shouldBe true
    game.performMove(asArrayOfInt("f4f5")) shouldBe false
    game.performMove(asArrayOfInt("e4f5")) shouldBe true
  }

  it should "not be moved diagonally if it's not a capture" in {
    game.performMove(asArrayOfInt("b5c4")) shouldBe false
  }
}
