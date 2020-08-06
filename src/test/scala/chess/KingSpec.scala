package chess

import chess.game.Game
import chess.game.model.Movement.asArrayOfInt
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class KingSpec extends AnyFlatSpec with should.Matchers {

  val game: Game = Game()

  "A King" should "not capture own pieces" in {
    game.performMove(asArrayOfInt("e1e2")) shouldBe false
  }

  "it" should "not skip over pieces" in {
    game.performMove(asArrayOfInt("e1e3")) shouldBe false
  }

  "it" should "capture enemy pieces" in {
    game.performMove(asArrayOfInt("e2e4")) shouldBe true
    game.performMove(asArrayOfInt("e7e5")) shouldBe true
    game.performMove(asArrayOfInt("e1e2")) shouldBe true
    game.performMove(asArrayOfInt("d7d5")) shouldBe true
    game.performMove(asArrayOfInt("e2e3")) shouldBe true
    game.performMove(asArrayOfInt("d5e4")) shouldBe true
    game.performMove(asArrayOfInt("e3d3")) shouldBe false
    game.performMove(asArrayOfInt("e3e4")) shouldBe true
  }

  "it" should "move one square only in any direction" in {
    game.performMove(asArrayOfInt("h7h6")) shouldBe true
    game.performMove(asArrayOfInt("e4d5")) shouldBe false
    game.performMove(asArrayOfInt("e4d4")) shouldBe false
    game.performMove(asArrayOfInt("e4d3")) shouldBe false
    game.performMove(asArrayOfInt("e4f5")) shouldBe false
    game.performMove(asArrayOfInt("e4f4")) shouldBe false
    game.performMove(asArrayOfInt("e4f3")) shouldBe true
  }
}
