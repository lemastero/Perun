package Perun

/**
 *
 *
 * Category Theory:
 * coproduct in category of pointed types (types with dedicated element - Option doe it for free)
 * wedge sum https://ncatlab.org/nlab/show/wedge+sum
 *
 * (1 + A) + (1 + B) simplify to (1 + A + B)
 * Either[Option[A],Option[B]]   Option[Either[A,B]]
 *
 *                a
 *                |
 * Nowhere -------|
 *                |
 *                b
 */
sealed trait Wedge[A,B]
object Wedge {
  case object Nowhere extends Wedge[Nothing, Nothing]
  case class Here[A,B](a: A) extends Wedge[A,B]
  case class There[A,B](b: B) extends Wedge[A,B]
}
