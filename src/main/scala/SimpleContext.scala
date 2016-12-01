import java.sql.Date

import io.hydrosphere.mist.lib.{MistJob, SQLSupport}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

trait MyMistLocalJob {
  def runTheJOb(session: SparkSession, parameters: Map[String, Any]): Map[String, Any]
}

object SimpleContext extends MistJob with SQLSupport with MyMistLocalJob {
  /** Contains implementation of spark job with ordinary [[org.apache.spark.SparkContext]]
    * Abstract method must be overridden
    *
    * based on https://github.com/Hydrospheredata/mist/blob/master/examples/src/main/scala/SimpleContext.scala
    *
    * @param parameters user parameters
    * @return result of the job
    */
  override def doStuff(parameters: Map[String, Any]): Map[String, Any] = {
    runTheJOb(session, parameters)
  }

  override def runTheJOb(session: SparkSession, parameters: Map[String, Any]): Map[String, Any] = {
    val numbers: Seq[Int] = parameters("digits").asInstanceOf[Seq[Int]]
    val rdd = session.sparkContext.parallelize(numbers)

    //val myMapResult = myDf.map { case Row(a, b) => Map("a" -> a, "b" -> b) }.collect
    // TODO collect async
    Map("result" -> rdd.map(x => x * 3).collect())
  }
}

object DirectRunner extends App {
  val parameters: Map[String, Any] = Map("digits" -> Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 0))

  Logger.getLogger("org").setLevel(Level.WARN)
  val logger: Logger = Logger.getLogger(this.getClass)

  // TODO I want to better understand mist's spark session support. How are the resources configured?
  val conf: SparkConf = new SparkConf()
    .setAppName("sparkApiSample")
    .setMaster("local[*]")
    .set("spark.executor.memory", "2G")
    .set("spark.executor.cores", "4")
    .set("spark.default.parallelism", "4")
    .set("spark.driver.memory", "1G")
    .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    //    .set("spark.kryo.registrationRequired", "true")
    .registerKryoClasses(Array(classOf[Date]))

  val session: SparkSession = SparkSession
    .builder()
    .config(conf)
    .getOrCreate()

  //TODO for simple running / debugging of the job I would like to use a run method like this. How can I access / create a spark session?
  // is this considered elegant using my own interface / method?
  val result = SimpleContext.runTheJOb(session, parameters)

  result.get("result").toSeq.asInstanceOf[List[Array[Int]]].flatten.foreach(println)
  session.stop
}
