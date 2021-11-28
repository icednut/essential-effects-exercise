package com.innerproduct.ee.effects.exercise01

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration

object Timing extends App {

  val clock: MyIO[Long] = MyIO[Long](() => System.currentTimeMillis())
  val timedHello = Timing.time(MyIO.putStr("hello"))

  def time[A](action: MyIO[A]): MyIO[(FiniteDuration, A)] =
    for {
      msg <- action
      now <- clock
    } yield (new FiniteDuration(System.currentTimeMillis() - now, TimeUnit.MILLISECONDS), msg)

  timedHello.unsafeRun() match {
    case (duration, _) => println(s"'hello' took $duration")
  }
}
