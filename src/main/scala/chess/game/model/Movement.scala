package chess.game.model

import chess.game.Player.Player
import chess.game.pieces.Piece

/**
 * Movement model.
 *
 * @author <a href="mailto:marius.gligor@gmail.com">Marius Gligor</a>
 */
object Movement {

  case class Move(colFrom: Int, rowFrom: Int, colTo: Int, rowTo: Int, diffRow: Int, diffCol: Int)

  case class Moved(piece: String, player: Player, move: Move, isCheck: Boolean = false)

  /**
   * Transform movement coordinates into a chess notation string.
   *
   * @param piece Piece to be moved
   * @param move  Movement coordinates
   * @return movement as a chess notation string
   */
  def asChessNotation(piece: Piece, move: Move): String =
    s"${piece.label} ${asc(move.colFrom)}${move.rowFrom + 1} " +
      s"${asc(move.colTo)}${move.rowTo + 1}"

  /**
   * Convert integer value to ascii
   *
   * @param value input value
   * @return ascii character
   */
  private def asc(value: Int) = (value + 97).toChar

  /**
   * Convert column character to integer
   *
   * @param value input value
   * @return integer value
   */
  private def col(value: Char) = value - 97

  /**
   * Convert row character to integer
   *
   * @param value input value
   * @return integer value
   */
  private def row(value: Char) = value - 49

  /**
   * Transform movement chess notation into movement coordinates.
   *
   * @param moveString movement chess notation string
   * @return movement coordinates
   */
  def asArrayOfInt(moveString: String): Array[Int] = {
    val move = moveString.toLowerCase()
    val fromCol = col(move.charAt(0))
    val fromRow = row(move.charAt(1))
    val toCol = col(move.charAt(2))
    val toRow = row(move.charAt(3))
    Array(fromCol, fromRow, toCol, toRow)
  }

  /**
   * Transform move coordinates into a Move model.
   * Movement deltas are also calculated and included.
   *
   * @param move Movement coordinates
   * @return Move model
   */
  def asMove(move: Array[Int]): Move = {
    val diffRow = move(3) - move(1)
    val diffCol = move(0) - move(2)
    Move(move(0), move(1), move(2), move(3), diffRow, diffCol)
  }

  /**
   * Validate movement coordinates
   *
   * @param move array of coordinates
   * @return <code>true</code> if valid coordinates <code>false</code> if not
   */
  def validateCoordinates(move: Array[Int]): Boolean = {
    for (i <- move.indices) {
      if (move(i) < 0 || move(i) > 7) {
        return false
      }
    }
    true
  }

  /**
   * Rotate move. From the input interface coordinates are coming top left originated
   * and are changed to be bottom left originated.
   *
   * @param move    initial movement
   * @param enabled enable rotation
   * @return rotate movement if enabled otherwise original movement
   */
  def rotate(move: Array[Int], enabled: Boolean = true): Array[Int] = {
    if (enabled) {
      move(1) = 7 - move(1)
      move(3) = 7 - move(3)
    }
    move
  }
}
