name := "sparkMist"
organization := "api"
version := "0.0.1.SNAPSHOT"

scalaVersion := "2.11.8"
lazy val spark = "2.0.2"

scalacOptions ++= Seq("-deprecation", "-feature")

//The default SBT testing java options are too small to support running many of the tests
// due to the need to launch Spark in local mode.
javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled")
parallelExecution in Test := false


//TODO how to easily switch setup betwen sbt run and assembly e.g. using a merge stragety and not provided
// to support both modi of local run and assembly to jar
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % spark % "provided",
  "org.apache.spark" %% "spark-sql" % spark % "provided",
  "io.hydrosphere" %% "mist" % "0.6.5" % "provided",
  "com.holdenkarau" %% "spark-testing-base" % "2.0.2_0.4.7" % "test"
)

mainClass := Some("DirectRunner")

//excludedJars in assembly := {
//  val cp = (fullClasspath in assembly).value
//  cp filter {f =>
//    f.data.getName.contains("spark"),
//    f.data.getName.startsWith("jar_name")
//  }
//}
