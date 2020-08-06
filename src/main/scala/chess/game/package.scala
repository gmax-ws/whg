package chess

package object game {

  object Player extends Enumeration {
    type Player = Value
    val White, Black = Value
  }
}
