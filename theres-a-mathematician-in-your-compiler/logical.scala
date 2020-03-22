package logical

trait IsNot[A, B]

object IsNot {

  implicit def instance[A, B]: A IsNot B = new IsNot[A, B] {}

  implicit def instanceEquality[A, B](implicit proof: A =:= B): A IsNot B = new IsNot[A, B] {}

}

object Example1 {

  trait B

  def foo[A](a: A)(implicit proof: IsNot[A, B]): String = a.toString + " is not a B!"

  foo(5)
  foo("a string")
  // foo(new B {})

  def bar[A](a: A)(implicit proof1: A IsNot Int, proof2: A IsNot String): String = a.toString

  bar(true)
  // bar(4)
  // bar("4")

}

object Example2 {

  import Nat._

  trait IsEven[N <: Nat]

  object IsEven {
    implicit def zeroCase: IsEven[_0] = new IsEven[_0] {}
    // Or equivalently:
    // implicit def zeroCase(implicit d: DummyImplicit): IsEven[_0] = new IsEven[_0] {}
    implicit def inductive[N <: Nat](implicit proof: IsEven[N]): IsEven[Succ[Succ[N]]] = new IsEven[Succ[Succ[N]]] {}
  }

  implicitly[IsEven[_0]]
  // implicitly[IsEven[_1]]
  implicitly[IsEven[_2]]
  // implicitly[IsEven[_3]]
  implicitly[IsEven[_4]]
  // implicitly[IsEven[_5]]
  implicitly[IsEven[_6]]
}


trait Nat
trait Succ[T <: Nat] extends Nat
object Nat {
  trait _0 extends Nat
  type _1 = Succ[_0]
  type _2 = Succ[_1]
  type _3 = Succ[_2]
  type _4 = Succ[_3]
  type _5 = Succ[_4]
  type _6 = Succ[_5]
}
