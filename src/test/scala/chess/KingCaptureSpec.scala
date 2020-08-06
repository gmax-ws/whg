package chess

import chess.game.Game
import chess.game.model.Movement.asArrayOfInt
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class KingCaptureSpec extends AnyFlatSpec with should.Matchers {

  val game: Game = Game()

  "King" should "not be captured" in {
    game.performMove(asArrayOfInt("e2e4")) shouldBe true
    game.performMove(asArrayOfInt("e7e5")) shouldBe true
    game.performMove(asArrayOfInt("f1c4")) shouldBe true
    game.performMove(asArrayOfInt("b8c6")) shouldBe true
    game.performMove(asArrayOfInt("d1f3")) shouldBe true
    game.performMove(asArrayOfInt("d7d6")) shouldBe true
    game.performMove(asArrayOfInt("f3f7")) shouldBe true
    game.performMove(asArrayOfInt("f7e8")) shouldBe false
  }
}
