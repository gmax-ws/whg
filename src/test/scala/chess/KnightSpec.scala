package chess

import chess.game.Game
import chess.game.model.Movement.asArrayOfInt
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class KnightSpec extends AnyFlatSpec with should.Matchers {

  val game: Game = Game()

  "A Knight" should "be moved in an 'L' pattern 2 rows and1 column or 2 columns 1 and row" in {
    game.performMove(asArrayOfInt("b1c3")) shouldBe true
    game.performMove(asArrayOfInt("b8d6")) shouldBe false
  }

  it should "be moved in 'L' pattern only" in {
    game.performMove(asArrayOfInt("b8c6")) shouldBe true
    game.performMove(asArrayOfInt("h2h3")) shouldBe true
    game.performMove(asArrayOfInt("c6c5")) shouldBe false
    game.performMove(asArrayOfInt("c6b6")) shouldBe false
    game.performMove(asArrayOfInt("c6d6")) shouldBe false
    game.performMove(asArrayOfInt("c6b5")) shouldBe false
    game.performMove(asArrayOfInt("c6a4")) shouldBe false
    game.performMove(asArrayOfInt("c6h6")) shouldBe false
  }

  it should "not capture it own pieces but can capture enemy pieces" in {
    game.performMove(asArrayOfInt("d7d5")) shouldBe true
    game.performMove(asArrayOfInt("c3a2")) shouldBe false
    game.performMove(asArrayOfInt("c3d5")) shouldBe true
  }
}
