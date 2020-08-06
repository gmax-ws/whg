package chess

import chess.game.Game
import chess.game.model.Movement.asArrayOfInt
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class InCheckSpec extends AnyFlatSpec with should.Matchers {

  val game: Game = Game()

  "Test 'check' status" should "in this game" in {
    game.performMove(asArrayOfInt("e2e4")) shouldBe true
    game.performMove(asArrayOfInt("e7e5")) shouldBe true
    game.performMove(asArrayOfInt("f1c4")) shouldBe true
    game.performMove(asArrayOfInt("b8c6")) shouldBe true
    game.performMove(asArrayOfInt("d1f3")) shouldBe true
    game.performMove(asArrayOfInt("d7d6")) shouldBe true
    game.isInCheck shouldBe false
    game.performMove(asArrayOfInt("f3f7")) shouldBe true
    game.isInCheck shouldBe true
  }

  "Test in 'check status'" should "in this game" in {
    game.performMove(asArrayOfInt("e8e7")) shouldBe false
    game.isInCheck shouldBe true
  }
}
