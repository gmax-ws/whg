package chess.game

import chess.game.board.ChessBoard
import chess.game.model.Movement.{asArrayOfInt, asMove, validateCoordinates}
import chess.utils.Logger

import scala.io.Source
import scala.util._

/**
 * Game object.
 *
 * @author <a href="mailto:marius.gligor@gmail.com">Marius Gligor</a>
 * @param visible <code>true</code> to show board (default) <code>false</code> to not show
 */
class Game(visible: Boolean) {

  private val logger = Logger(Game.getClass.getSimpleName)

  /* Game chess board */
  private val table = ChessBoard(visible)

  /**
   * Play a new game getting movements from a specific file.
   *
   * @param fileName File name containing game movements.
   * @return exit code 0 = success 1 = failure
   */
  def play(fileName: String): Int = {
    logger.info(s"Playing game from file $fileName")

    Using(Source.fromFile(fileName)) { source =>
      source.getLines().foreach(move => performMove(asArrayOfInt(move)))
    }.toEither match {
      case Right(_) =>
        logger.info("Game is over!")
        0
      case Left(e) =>
        logger.fatal("The game ended with an error", e)
        1
    }
  }

  /**
   * Perform move.
   * This method has been created to be used in tests as well
   *
   * @param move Movement coordinates
   * @return code>true</code> if move successfully done <code>false</code> otherwise
   */
  def performMove(move: Array[Int]): Boolean = {
    if (validateCoordinates(move)) {
      table.move(asMove(move))
    } else {
      logger.error("Movement coordinates are outside the table!")
      false
    }
  }

  /**
   * Detect "in check" status
   *
   * @return code>true</code> if "in check" status is detected <code>false</code> otherwise
   */
  def isInCheck: Boolean = table.isInCheck
}

object Game {
  /**
   * Game factory
   *
   * @param visible <code>true</code> to show board (default) <code>false</code> to not show
   * @return new Game
   */
  def apply(visible: Boolean = true): Game = new Game(visible)
}