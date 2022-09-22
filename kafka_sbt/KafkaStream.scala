package KafkaStream

import org.apache.spark.sql.SparkSession

object kafkastream {

   def main( args: Array[String] ) {

      val spark = SparkSession.builder().appName("kafka consumer").getOrCreate()
	  
      val dataFrame = spark.readStream.format("kafka").option("kafka.bootstrap.servers", "kfk1:9092,kfk2:9092,kfk3:9092").option("subscribe", "test").load()
	  
	  val query = dataFrame.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)").writeStream.outputMode("append").format("console").start().awaitTermination()
   }
}