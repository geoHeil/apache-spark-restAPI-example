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

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % spark % "provided",
  "org.apache.spark" %% "spark-sql" % spark % "provided",
  "io.hydrosphere" %% "mist" % "0.6.5" % "provided",
  "org.apache.spark" %% "spark-hive" % spark % "test",
  "com.holdenkarau" %% "spark-testing-base" % "2.0.2_0.4.7" % "test"
)

mainClass := Some("DirectRunner")
// http://stackoverflow.com/questions/18838944/how-to-add-provided-dependencies-back-to-run-test-tasks-classpath/21803413#21803413
run in Compile <<= Defaults.runTask(fullClasspath in Compile, mainClass in(Compile, run), runner in(Compile, run))
