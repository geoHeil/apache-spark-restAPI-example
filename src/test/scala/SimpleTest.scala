import com.holdenkarau.spark.testing.{DatasetSuiteBase, SharedSparkContext}
import org.scalatest.FunSuite

class SimpleTest extends FunSuite with SharedSparkContext with DatasetSuiteBase {

  test("SimpleContext should multiply input numbers by 3") {
    val session = spark

    val input: Map[String, Any] = Map("digits" -> Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 0))
    val expectedOutput: Map[String, Any] = Map("digits" -> Seq(3, 6, 9, 12, 15, 18, 21, 24, 27, 0))
    val result = SimpleContext.runTheJOb(session, input)
    assert(expectedOutput === result)
  }
}
