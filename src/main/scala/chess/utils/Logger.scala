package chess.utils

object LoggerLevel extends Enumeration {
  type LoggerLevel = Value
  val DEBUG, INFO, WARN, ERROR, FATAL = Value
}

import LoggerLevel._

/**
 * A simple console logger. Scala Logging is asynchronous for performances reasons.
 * For our test purposes we need a synchronous logger just to see the log messages in order.
 *
 * @author <a href="mailto:marius.gligor@gmail.com">Marius Gligor</a>
 */
class Logger(name: String, level: LoggerLevel = DEBUG) {

  private def isLevelEnabled(l: LoggerLevel) = l.id >= level.id

  def debug(message: Any): Unit =
    if (isLevelEnabled(DEBUG))
      println(s"[$DEBUG][$name] $message")

  def warn(message: Any): Unit =
    if (isLevelEnabled(WARN))
      println(s"[$WARN ][$name] $message")

  def info(message: Any): Unit =
    if (isLevelEnabled(INFO))
      println(s"[$INFO ][$name] $message")

  def error(message: Any): Unit =
    if (isLevelEnabled(ERROR))
      println(s"[$ERROR][$name] $message")

  def error(message: Any, th: Throwable, errorLevel: LoggerLevel = ERROR): Unit = {
    if (isLevelEnabled(errorLevel)) {
      val stackTrace = th.getStackTrace.mkString("\tat ", "\n\tat ", "")
      println(s"[$errorLevel][$name] $message\n${th.getClass.getName}: ${th.getMessage}\n$stackTrace")
      // th.printStackTrace()
    }
  }

  def fatal(message: Any, th: Throwable): Unit = error(message, th, FATAL)
}

object Logger {
  def apply(name: String, level: LoggerLevel = INFO) =
    new Logger(name.split('$').head, level)
}