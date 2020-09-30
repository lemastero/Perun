package Perun

sealed trait Smash[+A,+B]
object Smash {
  case object Nada extends Smash[Nothing, Nothing]
  case class Smash1[A,B](a: A, b: B) extends Smash[A,Nothing]
}
