name := "kafka-consumer"

version := "1.0.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.3.2" % "provided",
  "org.apache.spark" %% "spark-sql" % "2.3.2" % "provided",
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.3.2" % "provided",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.3.2" % "provided"
)
