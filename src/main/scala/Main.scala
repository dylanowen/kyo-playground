import kyo.*
import kyo.kernel.{ArrowEffect, Const}
import kyo.Result.Success
import kyo.Result.Error

type Warning = Emit[String]
val Warning: Emit.type = Emit

object Main {

  def main(args: Array[String]): Unit = {
    runPrint(14)
    runPrint(8)
    runPrint(5)
    runPrint(-4)
  }

  private def runPrint(value: Int): Unit = {
    run(value) match {
      case Success((warnings, result)) =>
        println(s"Result: $result")
        warnings.foreach(warning => println(s"\tWarning: $warning"))
      case Error(error) =>
        println(s"Error: $error")
    }
  }

  private def run(value: Int): Result[String, (Chunk[String], Int)] ={
    val effect = divideBy2(value)
    val warnings: (Chunk[String], Int) < Abort[String] = Warning.run(effect)
    val result = Abort.run[String](warnings)

    result.eval
  }
}

def divideBy2(input: Int): Int < (Abort[String] & Warning) = {
  val test = warnForNegative(input)
  for {
    _ <- verifyEven(input)
    result <- unsafeDivideBy2(input)
    _ <- if (input > 0) {
      (): Unit < Warning
    } else {
      Warning("input is negative")
    }
    _ <- warnForLessThanTen(input)
  } yield result
}

def verifyEven(input: Int): Unit < Abort[String] = {
  if (input % 2 == 0) {
    ()
  } else {
    Abort.fail("input is odd")
  }
}

def warnForNegative(input: Int): Warning.Ack < Warning = {
  if (input > 0) {
    Warning.Ack.Continue()
  } else {
    Warning("input is negative")
  }
}

def warnForLessThanTen(input: Int): Warning.Ack < Warning = {
  if (input >= 10) {
    Warning.Ack.Continue()
  } else {
    Warning("input is less than 10")
  }
}

def unsafeDivideBy2(input: Int): Int < Any = {
  input / 2
}

