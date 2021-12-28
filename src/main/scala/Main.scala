import cats.Monad
import cats.effect.IO
import cats.effect.unsafe.IORuntime
import cats.effect.unsafe.implicits.global

import scala.io.StdIn.readLine

object Main extends scala.App {
  case class Cat(age: Int, name: String)
  val cat = Cat(10, "Boris")
  val cat1InABox: IO[Cat] = IO(cat)
  val cat2InABox: IO[Cat] = IO(cat).map(_.copy(age = 20))

  val cat3InABox: IO[IO[Cat]] = cat1InABox.map(
    cat1 => cat2InABox.map(
      cat2 => Cat(cat1.age + cat2.age, cat1.name + cat2.name)
    )
  )

  val putACatToTheBox: Cat => IO[Cat] =
    IO(_)

  val aLotOfBoxesWithCats: List[IO[Cat]] = List(cat, cat, cat, cat, cat)
    .map(putACatToTheBox)

  // unsafeRunSync нельзя использовать
  val boxWithALotOfCats: IO[List[Cat]] = ???
}
