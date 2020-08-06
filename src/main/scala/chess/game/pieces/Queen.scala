package chess.game.pieces

import chess.game.Player._
import chess.game.board.ChessBoard
import chess.game.model.Movement.Move

/**
 * Queen implementation.
 *
 * @param player Player color White or Black
 * @author <a href="mailto:marius.gligor@gmail.com">Marius Gligor</a>
 */
class Queen(player: Player) extends Piece(player) {

  override val label: String = if (player == White) "Q" else "q"

  /**
   * The Queen moves any number of vacant squares horizontally, vertically, or diagonally.
   *
   * @param move movement coordinates
   * @return <code>true</code> if is a queen movement, <code>false</code> if not
   */
  private def isQueenMovement(move: Move) =
    isHorizontalMovement(move) || isVerticalMovement(move) || isDiagonalMovement(move)

  /**
   * Queen movement validation.
   * The Queen moves any number of vacant squares horizontally, vertically, or diagonally.
   *
   * @param table Chess table instance
   * @param move  Move coordinates
   * @return <code>true</code> if is a valid movement, <code>false</code> otherwise
   */
  override def isValidMovement(table: ChessBoard, move: Move): Boolean =
    if (isQueenMovement(move)) isVacantPath(table, move) else false
}

object Queen {
  def apply(player: Player): Queen = new Queen(player)
}
