package chess

import chess.game.Game
import chess.game.model.Movement.asArrayOfInt
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class BishopSpec extends AnyFlatSpec with should.Matchers {

  val game: Game = Game()

  "A Bishop" should "not skip over other pieces" in {
    game.performMove(asArrayOfInt("c1g5")) shouldBe false
    game.performMove(asArrayOfInt("d2d4")) shouldBe true
    game.performMove(asArrayOfInt("d7d5")) shouldBe true
    game.performMove(asArrayOfInt("c1g5")) shouldBe true
  }

  it should "capture enemy pieces" in {
    game.performMove(asArrayOfInt("e7e6")) shouldBe true
    game.performMove(asArrayOfInt("g5d8")) shouldBe true
  }

  it should "not capture own pieces" in {
    game.performMove(asArrayOfInt("h7h6")) shouldBe true
    game.performMove(asArrayOfInt("e2e3")) shouldBe true
    game.performMove(asArrayOfInt("h6h5")) shouldBe true
    game.performMove(asArrayOfInt("f1d3")) shouldBe true
    game.performMove(asArrayOfInt("h5h4")) shouldBe true
    game.performMove(asArrayOfInt("d3c2")) shouldBe false
  }

  it should "move diagonally only" in {
    game.performMove(asArrayOfInt("d3a3")) shouldBe false
    game.performMove(asArrayOfInt("d3d2")) shouldBe false
    game.performMove(asArrayOfInt("d3b5")) shouldBe true
  }
}
