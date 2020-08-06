package chess.game.pieces

import chess.game.Player._
import chess.game.board.ChessBoard
import chess.game.model.Movement.Move

/**
 * Bishop implementation.
 *
 * @param player Player color White or Black
 * @author <a href="mailto:marius.gligor@gmail.com">Marius Gligor</a>
 */
class Bishop(player: Player) extends Piece(player) {

  override val label: String = if (player == White) "B" else "b"

  /**
   * A bishop can moves any number of vacant squares diagonally.
   *
   * @param move movement coordinates
   * @return <code>true</code> if is a bishop movement, <code>false</code> if not
   */
  private def isBishopMovement(move: Move): Boolean =
    isDiagonalMovement(move)

  /**
   * Validate Bishop movement. A bishop can moves any number of vacant squares diagonally.
   *
   * @param table Chess table instance
   * @param move  Move coordinates
   * @return <code>true</code> if is a valid movement, <code>false</code> otherwise
   */
  override def isValidMovement(table: ChessBoard, move: Move): Boolean =
    if (isBishopMovement(move)) isVacantPath(table, move) else false
}

object Bishop {
  def apply(player: Player): Bishop = new Bishop(player)
}
