package Perun

/**
 * Represent one of two values, or both or non of them.
 *
 * Category theory
 * Pointed product https://ncatlab.org/nlab/show/pointed+object#limits_and_colimits
 * pointed type for A   Option[A]
 * (1 + a) * (1 + b)    (Either[Option[A], Option[B]])
 * 1 + a + b + a*b      Option[Either[Either[A,B],(A,B)]]
 *         a
 *         |
 * Non ----|---- (a,b)
 *         |
 *         b
 */
sealed trait Can[+A,+B]
// Show, Equal, Ord
object Can {
  case object Non extends Can[Nothing,Nothing]
  case class One[A](a: A) extends Can[A,Nothing]
  case class Eno[B](b: B) extends Can[Nothing,B]
  case class Two[A,B](a: A, b: B) extends Can[A,B]

  // eliminators

  /**
   * Case elimination for the Can datatype
   *
   * @param c default value to supply for the Non case
   * @param f eliminator for the One case
   * @param g eliminator for the Eno case
   * @param h eliminator for the Two case
   *
   * @return c
   */
  def can[A,B,C](c: C, f: A => C, g: B => C, h: (A,B) => C): Can[A,B] => C = {
    case Non => c
    case One(a) => f(a)
    case Eno(b) => g(b)
    case Two(a, b) => h(a,b)
  }

  // combinators

  /**
   * Projection that give you left value of data type (like _.1 for tuple)
   */
  def canFst[A,B]: Can[A,B] => Option[A] = {
    case One(a) => Some(a)
    case Two(a,_) => Some(a)
    case _ => None
  }

  /**
   * Projection that give you right value of data type (like _.2 for tuple)
   */
  def canSnd[A,B]: Can[A,B] => Option[B] = {
    case Eno(b) => Some(b)
    case Two(_,b) => Some(b)
    case _ => None
  }

  def isOne[A,B]: Can[A,B] => Boolean = {
    case _: One[_] => true
    case _ => false
  }

  def isEno[A,B]: Can[A,B] => Boolean = {
    case _: Eno[_] => true
    case _ => false
  }

  def isTwo[A,B]: Can[A,B] => Boolean = {
    case _: Two[_,_] => true
    case _ => false
  }

  def isNon[A,B]: Can[A,B] => Boolean = {
    case Non => true
    case _ => false
  }

  // Filtering

//  def ones[F[_]: Foldable,A,B](fa: F[Can[A,B]]): List[A] =
//    fa.foldl(List.empty[A]) {
//      case(acc, One(a)) => a :: acc
//      case(acc, _) => acc
//    }
//
//  def twos[F[_]: Foldable,A,B](fa: F[Can[A,B]]): List[(A,B)] =
//    fa.foldl(List.empty[(A,B)]) {
//      case(acc, Two(a,b)) => (a,b) :: acc
//      case(acc, _) => acc
//    }

  // TODO

  // Distributivity

  /** Distribute Can value over a product */
  def distributeCan[A,B,C]: Can[(A,B),C] => (Can[A,C], Can[B,C]) = {
    case Non => (Non, Non)
    case One((a,b)) => (One(a), One(b))
    case Eno(c) => (Eno(c), Eno(c))
    case Two((a,b),c) => (Two(a,c), Two(b,c))
  }

  def codistributeCan[A,B,C]: Either[Can[A,C],Can[B,C]] => Can[Either[A,B],C] = {
    case Left(Non) => Non
    case Left(One(a)) => One(Left(a))
    case Left(Eno(c)) => Eno(c)
    case Left(Two(a,c)) => Two(Left(a),c)
    case Right(Non) => Non
    case Right(One(a)) => One(Right(a))
    case Right(Two(b,c)) => Two(Right(b),c)
  }

  // Associativity
}
