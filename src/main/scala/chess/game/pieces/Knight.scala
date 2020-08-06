package chess.game.pieces

import chess.game.Player._
import chess.game.board.ChessBoard
import chess.game.model.Movement.Move

/**
 * Knight implementation.
 *
 * @param player Player color White or Black
 * @author <a href="mailto:marius.gligor@gmail.com">Marius Gligor</a>
 */
class Knight(player: Player) extends Piece(player) {

  override val label: String = if (player == White) "N" else "n"

  /**
   *  A knight piece moves in an "L" pattern.
   *
   * @param move movement coordinates
   * @return <code>true</code> if is a knight movement, <code>false</code> if not
   */
  private def isKnightMovement(move: Move): Boolean = {
    (Math.abs(move.diffRow) == 1 && Math.abs(move.diffCol) == 2) ||
      (Math.abs(move.diffRow) == 2 && Math.abs(move.diffCol) == 1)
  }

  /**
   * Knight movement validation. A knight moves in an "L" pattern.
   * This can be thought of as moving two squares horizontally then one square vertically,
   * or moving one square horizontally then two squares vertically
   * The knight is not blocked by other pieces: it jumps to the new location.
   *
   * @param table Chess table instance
   * @param move  Move coordinates
   * @return <code>true</code> if is a valid movement, <code>false</code> otherwise
   */
  override def isValidMovement(table: ChessBoard, move: Move): Boolean =
    isKnightMovement(move)
}

object Knight {
  def apply(player: Player): Knight = new Knight(player)
}
