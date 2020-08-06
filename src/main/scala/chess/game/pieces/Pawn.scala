package chess.game.pieces

import chess.game.Player._
import chess.game.board.ChessBoard
import chess.game.model.Movement.Move

/**
 * Pawns have the most complex rules of movement:
 * A pawn moves straight forward one square, if that square is vacant.
 * If it has not yet moved, a pawn also has the option of moving two squares straight forward, provided both squares
 * are vacant. Pawns cannot move backwards. Pawns are the only pieces that capture differently from how they move.
 * A pawn can capture an enemy piece on either of the two squares diagonally in front of the pawn
 * (but cannot move to those squares if they are vacant).
 * The pawn is also involved in the two special moves en passant and promotion (not yet implemented).
 *
 * @param player Player color White or Black
 * @author <a href="mailto:marius.gligor@gmail.com">Marius Gligor</a>
 */
class Pawn(player: Player) extends Piece(player) {

  override val label: String = if (player == White) "P" else "p"

  /**
   * Validate one row movement.
   *
   * @param move Move coordinates
   * @return code>true</code> if is a valid movement, <code>false</code> otherwise
   */
  private def moveOneRow(move: Move) = {
    (move.diffRow == 1 && player == White) || (move.diffRow == -1 && player == Black)
  }

  /**
   * Validate two row movement. This is a sp
   *
   * @param table Chess table instance
   * @param move  Move coordinates
   * @return code>true</code> if is a valid movement, <code>false</code> otherwise
   */
  private def moveTwoRows(table: ChessBoard, move: Move) = {
    if (move.diffRow == 2 && player == White && move.rowFrom == 1) {
      table.isVacant(2, move.colFrom)
    } else if (move.diffRow == -2 && player == Black && move.rowFrom == 6) {
      table.isVacant(5, move.colFrom)
    } else {
      false
    }
  }

  /**
   * Check if it's a valid Pawn movement.
   * A Pawn can move only forward 1 or 2 positions on the same column.
   *
   * @param table Chess table instance
   * @param move  Move coordinates
   * @return code>true</code> if is a Pawn valid movement, <code>false</code> otherwise
   */
  def isValidPawnMovement(table: ChessBoard, move: Move): Boolean = {
    // movement, target must be empty
    if (table.isVacant(move.rowTo, move.colTo)) {
      if (moveOneRow(move)) true else moveTwoRows(table, move)
    } else {
      false
    }
  }

  /**
   * Check if it's a valid Pawn capture movement. When is a capture, the Pawn
   * move 1 row and 1 column forward and the final target must be an opponent player piece
   *
   * @param table Chess table instance
   * @param move  Move coordinates
   * @return code>true</code> if is a Pawn valid capture movement, <code>false</code> otherwise
   */
  def isValidPawnCapture(table: ChessBoard, move: Move): Boolean = {
    if (Math.abs(move.diffCol) == 1) {
      if (moveOneRow(move)) isValidTarget(table, move, isEmptyValid = false) else false
    } else {
      false
    }
  }

  /**
   * Validate Pawn movement.
   *
   * @param table Chess table instance
   * @param move  Move coordinates
   * @return <code>true</code> if is a valid movement, <code>false</code> otherwise
   */
  override def validate(table: ChessBoard, move: Move): Boolean = {
    val isMovement = move.diffCol == 0
    val isCapture = Math.abs(move.diffCol) == 1

    if (isMovement)
      isValidPawnMovement(table, move)
    else if (isCapture)
      isValidPawnCapture(table, move)
    else
      false
  }
}

object Pawn {
  def apply(player: Player): Pawn = new Pawn(player)
}
