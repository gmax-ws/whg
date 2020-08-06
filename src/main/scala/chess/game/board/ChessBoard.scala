package chess.game.board

import chess.game
import chess.game.Player._
import chess.game.model.Movement._
import chess.game.pieces._
import chess.utils.Logger

import scala.collection.mutable

/**
 * Chess board implementation.
 *
 * @author <a href="mailto:marius.gligor@gmail.com">Marius Gligor</a>
 * @param visible <code>true</code> to show board (default) <code>false</code> to not show
 */
class ChessBoard(visible: Boolean) {

  private val logger = Logger(ChessBoard.getClass.getSimpleName)

  /* Chess table is an 8x8 square */
  private val table = Array.ofDim[Option[Piece]](8, 8)

  /* Keep valid movements, useful for implementing 'en passant' and other features */
  private val tracer = mutable.Stack[Moved]()

  /**
   * Get piece at row and column
   *
   * @param row Row index
   * @param col Column index
   * @return Piece at row and column
   */
  private[chess] def get(row: Int, col: Int): Option[Piece] = table(row)(col)

  /**
   * Check if a table cell is empty
   *
   * @param row row index
   * @param col column index
   * @return <code>true</code> if cell is empty, <code>false</code> otherwise
   */
  private[chess] def isVacant(row: Int, col: Int): Boolean = table(row)(col).isEmpty

  /**
   * Detect "in check" status
   *
   * @return code>true</code> if "in check" status is detected <code>false</code> otherwise
   */
  private[chess] def isInCheck: Boolean = if (tracer.isEmpty) false else tracer.top.isCheck

  /**
   * Validate and perform movement
   *
   * @param move Movement parameters
   * @return code>true</code> if move successfully done <code>false</code> otherwise
   */
  private[chess] def move(move: Move): Boolean = {
    table(move.rowFrom)(move.colFrom) match {
      case Some(piece) =>
        logger.info(s"$toMoveNext player to move")
        logger.info(asChessNotation(piece, move))
        logger.debug(s"${piece.label} move from row=${move.rowFrom} col=${move.colFrom} " +
          s"to row=${move.rowTo} col=${move.colTo} diffRow=${move.diffRow} diffCol=${move.diffCol}")

        // check alternation to move White move first
        if (!isAlternateMove(piece)) {
          logger.error(s"Player ${piece.player} cannot move twice!")
          return false
        }

        // validate movement
        if (piece.validate(this, move)) {
          if (tryToMove(move, piece)) {
            // check if enemy king is in chess
            val isCheck = isChess(enemy(piece.player))
            if (isCheck)
              logger.warn("check!")

            // print the board
            showTable()

            // trace valid movements
            val moved = Moved(piece.label, piece.player, move, isCheck = isCheck)
            tracer.push(moved)
            true
          } else {
            false
          }
        } else {
          logger.error(s"Invalid movement for piece " +
            s"${piece.label} move from row=${move.rowFrom} col=${move.colFrom} to row=${move.rowTo} col=${move.colTo} " +
            s"diffRow=${move.diffRow} diffCol=${move.diffCol}")
          false
        }
      case None =>
        logger.error(s"Invalid movement, found no piece to move at row=${move.rowFrom} col=${move.colFrom}")
        false
    }
  }

  /**
   * Initialize table
   */
  private def init(): Unit = {

    // initialize whole table
    for (i <- 0 to 7; j <- 0 to 7) table(i)(j) = None
    // player 1 (white)
    table(0)(0) = Some(Rook(White))
    table(0)(1) = Some(Knight(White))
    table(0)(2) = Some(Bishop(White))
    table(0)(3) = Some(Queen(White))
    table(0)(4) = Some(King(White))
    table(0)(5) = Some(Bishop(White))
    table(0)(6) = Some(Knight(White))
    table(0)(7) = Some(Rook(White))
    for (i <- 0 to 7) table(1)(i) = Some(Pawn(White))
    // player 2 (black)
    table(7)(0) = Some(Rook(Black))
    table(7)(1) = Some(Knight(Black))
    table(7)(2) = Some(Bishop(Black))
    table(7)(3) = Some(Queen(Black))
    table(7)(4) = Some(King(Black))
    table(7)(5) = Some(Bishop(Black))
    table(7)(6) = Some(Knight(Black))
    table(7)(7) = Some(Rook(Black))
    for (i <- 0 to 7) table(6)(i) = Some(Pawn(Black))

    // print initialized chess board
    showTable()
  }

  /**
   * A naive chess board print using ASCII characters
   */
  private def showTable(): Unit = {
    if (visible) {
      print("\n    a b c d e f g h\n  -------------------")
      for (i <- 7 to 0 by -1) {
        print(s"\n${i + 1} | ")
        for (j <- 0 to 7) {
          table(i)(j) match {
            case Some(piece) => print(s"${piece.label} ")
            case None => print(". ")
          }
        }
        print(s"| ${i + 1}")
      }
      println("\n  -------------------\n    a b c d e f g h\n")
    }
  }

  /**
   * Perform movement. If target location is the King piece the movement is invalid.
   * King cannot be captured.
   *
   * @param move  movement parameters
   * @param piece piece to move
   * @return code>true</code> if move successfully done <code>false</code> otherwise
   */
  private def tryToMove(move: Move, piece: Piece): Boolean = {
    val target = table(move.rowTo)(move.colTo)
    if (isKing(target)) {
      logger.warn("King cannot be captured!")
      false
    } else {
      val pieceFrom = table(move.rowFrom)(move.colFrom)
      val pieceTo = table(move.rowTo)(move.colTo)

      table(move.rowTo)(move.colTo) = pieceFrom
      table(move.rowFrom)(move.colFrom) = None

      if (isChess(piece.player)) {
        logger.error("in check!")
        // rollback move
        table(move.rowFrom)(move.colFrom) = pieceFrom
        table(move.rowTo)(move.colTo) = pieceTo
        false
      } else {
        true
      }
    }
  }

  /**
   * Check if target cell is the King piece
   *
   * @param cell check if target location is King
   * @return code>true</code> if King piece is found <code>false</code> otherwise
   */
  private def isKing(cell: Option[Piece]): Boolean = {
    cell match {
      case Some(piece) => piece.label.compareToIgnoreCase("k") == 0
      case None => false
    }
  }

  /**
   * Find player enemy.
   *
   * @param player player
   * @return player opponent
   */
  private def enemy(player: Player): game.Player.Value = if (player == White) Black else White

  /**
   * Check if player king is in chess.
   * Check all enemy pieces to have a valid path toward the player king.
   *
   * @param player Player
   * @return code>true</code> if is in check <code>false</code> otherwise
   */
  private def isChess(player: Player): Boolean =
    findPlayerKing(player) match {
      case Some((col, row)) =>
        for {
          r <- 0 to 7
          c <- 0 to 7
          figure <- table(r)(c) if player != figure.player
        } {
          val toKingMove = asMove(Array(c, r, col, row))
          if (figure.validate(this, toKingMove)) {
            return true
          }
        }
        false
      case None =>
        false
    }

  /**
   * Find player king
   *
   * @param player player
   * @return Player king coordinates.
   */
  private def findPlayerKing(player: Player): Option[(Int, Int)] = {
    for {
      r <- 0 to 7
      c <- 0 to 7
      piece <- table(r)(c)
    } {
      if (piece.label.compareToIgnoreCase("k") == 0 && piece.player == player) {
        return Some((c, r))
      }
    }
    None
  }

  /**
   * Players MUST alternate movements. No one can move twice
   * White player move first.
   *
   * @param piece Piece to move
   * @return code>true</code> if twice movement detected <code>false</code> otherwise
   */
  private def isAlternateMove(piece: Piece): Boolean =
    if (tracer.isEmpty) piece.player == White else tracer.top.player != piece.player

  /**
   * Who is the player to move next
   *
   * @return Player to move
   */
  private def toMoveNext = if (tracer.isEmpty) White else enemy(tracer.top.player)

  // initialize table
  init()
}

object ChessBoard {
  /**
   * Create and init table.
   *
   * @param visible <code>true</code> to show board (default) <code>false</code> to not show
   * @return table already initialized
   */
  def apply(visible: Boolean = true): ChessBoard = new ChessBoard(visible)
}