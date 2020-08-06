package chess

import java.io.File

import cats.effect._
import chess.game.Game
import chess.utils.Logger

/**
 * Chess Programming Test
 *
 * @author <a href="mailto:marius.gligor@gmail.com">Marius Gligor</a>
 */
object Chess extends IOApp {

  private lazy val logger = Logger(Chess.getClass.getSimpleName)

  private def playFromFile(fileName: String) =
    for {
      n <- IO(Game().play(fileName))
    } yield ExitCode(n)

  private def helpUsage() = {
    val jarFile = new File(Chess.getClass.getProtectionDomain.getCodeSource.getLocation.toURI).getPath
    IO(logger.info(s"Usage:\n\tjava -jar $jarFile <file-name>")).as(ExitCode(2))
  }

  def run(args: List[String]): IO[ExitCode] =
    args.headOption match {
      case Some(fileName) => playFromFile(fileName)
      case None => helpUsage()
    }
}
