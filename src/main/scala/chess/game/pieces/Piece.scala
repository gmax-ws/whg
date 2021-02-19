package chess.game.pieces

import chess.game.Player.Player
import chess.game.board.ChessBoard
import chess.game.model.Movement.Move
import chess.game.pieces
import chess.utils.Logger

/**
 * Base class of chess pieces implementation.
 *
 * @param player Player color <code>White</code> or <code>Black</code>
 * @author <a href="mailto:marius.gligor@gmail.com">Marius Gligor</a>
 */
abstract class Piece(val player: Player) {

  private val logger = Logger(this.getClass.getSimpleName)

  /* Piece name */
  val label: String

  /**
   * Validate movement. First of all the target destination is validated.
   * If target is valid, then movement is validated. This validation
   * is used by all pieces except Pawns which have special validations.
   * Pawn override this method [[pieces.Pawn.validate()]].
   *
   * @param table Chess table instance
   * @param move  Move coordinates
   * @return <code>true</code> if is a valid movement, <code>false</code> otherwise
   */
  def validate(table: ChessBoard, move: Move): Boolean = {
    if (isValidTarget(table, move)) isValidMovement(table, move) else false
  }

  /**
   * Check if is a valid target. A valid target means that a piece is found
   * at destination cell (To) and belong to opponent player.
   *
   * @param table        Chess Table instance
   * @param move         Movement coordinates
   * @param isEmptyValid Is empty square considered valid, default <code>true</code>
   * @return <code>true</code> if is a valid target, <code>false</code> otherwise
   */
  def isValidTarget(table: ChessBoard, move: Move, isEmptyValid: Boolean = true): Boolean = {
    table.get(move.rowTo, move.colTo) match {
      case Some(piece) => piece.player != player
      case None => isEmptyValid
    }
  }

  /**
   * Validate movement coordinates. Each piece has a specific set of validations.
   * They must override this method and implement the specific validations.
   * The Pawn piece has special validation rules and use it own validation.
   *
   * @param table Chess table instance
   * @param move  Move coordinates
   * @return <code>true</code> if is a valid movement, <code>false</code> otherwise
   */
  protected def isValidMovement(table: ChessBoard, move: Move): Boolean = false

  /**
   * Check if path from source to destination is vacant.
   * A ptah is vacant valid if no other pieces are found on the path.
   *
   * @param table Chess Table instance
   * @param move  Movement coordinates
   * @return <code>true</code> if path is vacant, <code>false</code> otherwise
   */
  protected def isVacantPath(table: ChessBoard, move: Move): Boolean = {
    /**
     * Check HORIZONTAL vacant path
     *
     * @return <code>true</code> if is a vacant path, <code>false</code> otherwise
     */
    def horizontal(): Boolean = {
      val beg = Math.min(move.colFrom, move.colTo) + 1
      val end = Math.max(move.colFrom, move.colTo)

      // adjacent
      for (col <- beg until end if beg != end) {
        if (!table.isVacant(move.rowFrom, col)) {
          logger.debug(s"HORIZONTAL vacant cells test failed ${move.rowFrom}:$col")
          return false
        }
      }
      true
    }

    /**
     * Check VERTICAL vacant path
     *
     * @return <code>true</code> if is a vacant path, <code>false</code> otherwise
     */
    def vertical(): Boolean = {
      val beg = Math.min(move.rowFrom, move.rowTo) + 1
      val end = Math.max(move.rowFrom, move.rowTo)

      // adjacent
      for (row <- beg until end if beg != end) {
        if (!table.isVacant(row, move.colFrom)) {
          logger.debug(s"VERTICAL vacant cells test failed $row:${move.colFrom}")
          return false
        }
      }
      true
    }

    /**
     * Check DIAGONAL vacant path
     *
     * @return <code>true</code> if is a vacant path, <code>false</code> otherwise
     */
    def diagonal(): Boolean = {
      val rBeg = Math.min(move.rowFrom, move.rowTo) + 1
      val rEnd = Math.max(move.rowFrom, move.rowTo)

      val cBeg = Math.min(move.colFrom, move.colTo) + 1
      val cEnd = Math.max(move.colFrom, move.colTo)

      val colStep = if (move.diffCol < 0) 1 else -1

      val rows = for (r <- rBeg until rEnd) yield r
      val cols = for (c <- cBeg until cEnd by colStep) yield c
      val cells = rows zip cols

      for ((row, col) <- cells if rBeg != rEnd) {
        if (!table.isVacant(row, col)) {
          logger.debug(s"DIAGONAL vacant cells test failed $row:$col")
          return false
        }
      }
      true
    }

    if (isHorizontalMovement(move)) {
      // horizontally
      horizontal()
    } else if (isVerticalMovement(move)) {
      // vertically
      vertical()
    } else if (isDiagonalMovement(move)) {
      // diagonally
      diagonal()
    } else {
      // invalid
      false
    }
  }

  /**
   * Check if it's a diagonally movement
   *
   * @param move movement
   * @return <code>true</code> if is a diagonally movement, <code>false</code> otherwise
   */
  protected def isDiagonalMovement(move: Move): Boolean =
    Math.abs(move.diffRow) == Math.abs(move.diffCol)

  /**
   * Check if it's a horizontal movement
   *
   * @param move movement
   * @return <code>true</code> if is a horizontal movement, <code>false</code> otherwise
   */
  protected def isHorizontalMovement(move: Move): Boolean =
    move.diffRow == 0 && move.diffCol != 0

  /**
   * Check if it's a vertical movement
   *
   * @param move movement
   * @return <code>true</code> if is a vertical movement, <code>false</code> otherwise
   */
  protected def isVerticalMovement(move: Move): Boolean =
    move.diffRow != 0 && move.diffCol == 0
}
