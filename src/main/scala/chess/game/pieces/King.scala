package chess.game.pieces

import chess.game.Player._
import chess.game.board.ChessBoard
import chess.game.model.Movement.Move

/**
 * King implementation.
 *
 * @param player Player color White or Black
 * @author <a href="mailto:marius.gligor@gmail.com">Marius Gligor</a>
 */
class King(player: Player) extends Piece(player) {

  override val label: String = if (player == White) "K" else "k"

  /**
   * A King piece can move one square in any direction.
   *
   * @param move movement coordinates
   * @return <code>true</code> if is a king movement, <code>false</code> if not
   */
  private def isKingMovement(move: Move): Boolean = {
    val diff = (Math.abs(move.diffRow), Math.abs(move.diffCol))
    diff == (1, 0) || diff == (0, 1) || diff == (1, 1)
  }

  /**
   * Validate King movement coordinates. A King piece can move one square in any direction.
   * It's also moved when castling (not yet implemented).
   *
   * RESTRICTIONS:
   * He can capture a piece for opponent player only if the captured piece is not defended by another piece.
   * After moving to the destination location the King must not be in check
   * TODO - Restrictions are not yet validated
   *
   * @param table Chess table instance
   * @param move  Move coordinates
   * @return <code>true</code> if is a valid movement, <code>false</code> otherwise
   */
  override def isValidMovement(table: ChessBoard, move: Move): Boolean =
    isKingMovement(move)
}

object King {
  def apply(player: Player): King = new King(player)
}
