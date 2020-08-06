package chess

import chess.game.Game
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class GameFilePlaySpec extends AnyFlatSpec with should.Matchers {

  /* Do not show table */
  val game: Game = Game(false)

  "The game" should "return 1 if file not found and print stack trace" in {
    game.play("data/game.txt") shouldBe 1
  }

  it should "return 0 if file is found" in {
    game.play("data/checkmate.txt") shouldBe 0
  }
}
