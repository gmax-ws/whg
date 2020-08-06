package chess.game.pieces

import chess.game.Player._
import chess.game.board.ChessBoard
import chess.game.model.Movement.Move

/**
 * Rook implementation.
 *
 * @param player Player color White or Black
 * @author <a href="mailto:marius.gligor@gmail.com">Marius Gligor</a>
 */
class Rook(player: Player) extends Piece(player) {

  override val label: String = if (player == White) "R" else "r"

  /**
   * A Rook moves any number of vacant squares horizontally or vertically.
   *
   * @param move movement coordinates
   * @return <code>true</code> if is a queen movement, <code>false</code> if not
   */
  private def isRookMovement(move: Move) =
    isHorizontalMovement(move) || isVerticalMovement(move)

  /**
   * Rook movement validation.
   * A Rook moves any number of vacant squares horizontally or vertically.
   * It is also moved when castling (not yet implemented).
   *
   * @param table Chess table instance
   * @param move  Move coordinates
   * @return <code>true</code> if is a valid movement, <code>false</code> otherwise
   */
  override def isValidMovement(table: ChessBoard, move: Move): Boolean =
    if (isRookMovement(move)) isVacantPath(table, move) else false
}

object Rook {
  def apply(player: Player): Rook = new Rook(player)
}
