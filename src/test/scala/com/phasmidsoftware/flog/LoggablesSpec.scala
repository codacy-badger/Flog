/*
 * Copyright (c) 2019. Phasmid Software
 */

package com.phasmidsoftware.flog

import org.scalatest.flatspec
import org.scalatest.matchers.should

import scala.concurrent.Future
import scala.util.Try

//noinspection ScalaStyle
class LoggablesSpec extends flatspec.AnyFlatSpec with should.Matchers with Loggables {

  behavior of "Loggables"

  it should "optionLoggable" in {
    val target = optionLoggable[Int]
    target.toLog(Some(42)) shouldBe "Some(42)"
  }

  it should "mapLoggable" in {
    val target = mapLoggable[String, Int]()
    target.toLog(Map("x" -> 1, "y" -> 2)) shouldBe "{x:1,y:2}"
  }

  it should "listLoggable" in {
    val target = listLoggable[Int]
    target.toLog(List(42, 99, 101)) shouldBe "[42, ... (1 elements), ... 101]"
  }

  it should "tryLoggable" in {
    val target = tryLoggable[Int]
    target.toLog(Try("1".toInt)) shouldBe "Success(1)"
  }

  it should "futureLoggable" in {
    import Flog._

    import scala.concurrent.ExecutionContext.Implicits.global
    val target = futureLoggable[Int]
    // NOTE that this future task takes no time at all and, in any case,
    // we do not wait for the Future to complete.
    // See FlogSpec for a slightly more realistic example.
    val str = target.toLog(Future("1".toInt))
    str.replaceAll("""\(\S+\)""", "") shouldBe "Future: promise  created... "
  }

  it should "valueToLog" in {
    valueToLog[Int, (Int, Int)]((42, 99), 0) shouldBe "42"
    valueToLog[Int, (Int, Int)]((42, 99), 1) shouldBe "99"
  }

  it should "toLog1" in {
    case class Onesy(x: Int)
    val target = toLog1(Onesy)
    target.toLog(Onesy(42)) shouldBe "Onesy(x:42)"
  }

  it should "toLog2" in {
    case class Twosy(x: Int, y: Boolean)
    val target = toLog2(Twosy)
    target.toLog(Twosy(42, y = true)) shouldBe "Twosy(x:42,y:true)"
  }

  it should "toLog3" in {
    case class Threesy(x: Int, y: Boolean, z: Double)
    val target = toLog3(Threesy)
    target.toLog(Threesy(42, y = true, 3.1415927)) shouldBe "Threesy(x:42,y:true,z:3.1415927)"
  }

  it should "toLog4" in {
    case class Foursy(x: Int, y: Boolean, z: Double, q: String)
    val target = toLog4(Foursy)
    target.toLog(Foursy(42, y = true, 3.1415927, "x")) shouldBe "Foursy(x:42,y:true,z:3.1415927,q:x)"
  }

  it should "toLog4 with explicit field names" in {
    case class Foursy(x: Int, y: Boolean, z: Double, q: String)
    val target = toLog4(Foursy, Seq("x", "y", "z", "q"))
    target.toLog(Foursy(42, y = true, 3.1415927, "x")) shouldBe "Foursy(x:42,y:true,z:3.1415927,q:x)"
  }

  it should "toLog5" in {
    case class Fivesy(x: Int, y: Boolean, z: Double, q: String, r: BigInt)
    val loggable: Loggable[Fivesy] = toLog5(Fivesy)
    loggable.toLog(Fivesy(42, y = true, 3.1415927, "x", BigInt(99))) shouldBe "Fivesy(x:42,y:true,z:3.1415927,q:x,r:99)"
  }
}
