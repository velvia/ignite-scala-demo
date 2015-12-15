import org.apache.ignite.Ignition
import org.apache.ignite.lang.IgniteCallable

// Example of using Ignite's distributed compute API
object IgniteCompute extends App {
  import collection.JavaConverters._

  val ignite = Ignition.start()
  val inputStr = "Count characters using callable"

  // Iterate through all the words in the sentence and create Callable jobs.
  val calls = inputStr.split(" ").map { word =>
    new IgniteCallable[Int] {
      override def call(): Int = word.length
    }
  }

  // Execute collection of Callables on the grid.
  val res = ignite.compute().call(calls.toSeq.asJava)

  println(s"Total number of characters in '$inputStr' is ${res.asScala.sum}")
}