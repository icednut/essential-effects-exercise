package com.innerproduct.ee.effects.exercise02

import cats.effect._
import cats.implicits.catsSyntaxTuple2Semigroupal

import scala.concurrent.duration.DurationInt

object TickingClock extends IOApp {
  // My Solution
  val tickingClock: IO[Unit] = {
    def run(current: IO[Unit], count: Int): IO[Unit] =
      if (count >= 100) {
        IO.unit
      } else {
        (
          for {
            _ <- IO.sleep(1.seconds)
            _ <- IO.println(System.currentTimeMillis())
          } yield (),
          run(current, count + 1)).mapN((first, second) => second)
      }

    run(IO.unit, 0)
  }

  // Book Solution
  //  val tickingClock: IO[Unit] = for {
  //    _ <- IO(println(System.currentTimeMillis))
  //    _ <- IO.sleep(1.second)
  //    _ <- tickingClock
  //  } yield ()

  override def run(args: List[String]): IO[ExitCode] =
    tickingClock.as(ExitCode.Success)
}
