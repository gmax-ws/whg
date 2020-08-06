package chess

import chess.game.Game
import chess.game.model.Movement.asArrayOfInt
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class QueenSpec extends AnyFlatSpec with should.Matchers {

  val game: Game = Game()

  "A Queen" should "not skip over other pieces" in {
    game.performMove(asArrayOfInt("d1g4")) shouldBe false
    game.performMove(asArrayOfInt("e2e4")) shouldBe true
    game.performMove(asArrayOfInt("e7e5")) shouldBe true
    game.performMove(asArrayOfInt("d1g4")) shouldBe true
  }

  it should "not capture own pieces" in {
    game.performMove(asArrayOfInt("a7a5")) shouldBe true
    game.performMove(asArrayOfInt("g4g2")) shouldBe false
  }

  it should "capture enemy pieces" in {
    game.performMove(asArrayOfInt("g4g7")) shouldBe true
  }

  it should "be moved in any direction" in {
    game.performMove(asArrayOfInt("b7b6")) shouldBe true
    game.performMove(asArrayOfInt("g7e5")) shouldBe true
    game.isInCheck shouldBe true
    game.performMove(asArrayOfInt("h7h6")) shouldBe false
    game.isInCheck shouldBe true
    game.performMove(asArrayOfInt("f8e7")) shouldBe true
    game.isInCheck shouldBe false
  }
}
