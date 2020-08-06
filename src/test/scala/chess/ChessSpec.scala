package chess

import chess.game.Game
import chess.game.model.Movement.asArrayOfInt
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class ChessSpec extends AnyFlatSpec with should.Matchers {

  "White player" should "move first" in {
    val game: Game = Game()
    game.performMove(asArrayOfInt("g4g5")) shouldBe false
  }

  "White player" must "not move twice" in {
    val game: Game = Game()
    game.performMove(asArrayOfInt("g2g4")) shouldBe true
    game.performMove(asArrayOfInt("g4g5")) shouldBe false
  }

  "Black player" must "not move twice" in {
    val game: Game = Game()
    game.performMove(asArrayOfInt("a2a4")) shouldBe true
    game.performMove(asArrayOfInt("h7h5")) shouldBe true
    game.performMove(asArrayOfInt("g7g6")) shouldBe false
  }
}
