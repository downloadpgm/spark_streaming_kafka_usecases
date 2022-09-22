# spark_streaming_kafka_with_TFIDF
spark streaming kafka with TFIDF sentiment analysis
- Source : Kaggle Dataset
- URL : https://www.kaggle.com/crowdflower/twitter-airline-sentiment

# Purpose:
- use kafka to implement consumer and producer to be used by spark streaming.
- create object class to be the producer for kafka using spark.
- train a TFIDF algorithm using Virgin Airlines tweets for sentiment analysis and use in spark streaming for predictions

# Requirements:
- Spark installed
- Kafka library installled

# Objective :
- use kakfa producer to generate input data to be consumed be spark streaming.
- implement some logic inside the spark to consume data from kakfa, process data, and send results back to kafka.
- use object class in spark to be the engine to send results back to kakfa.

# To run Spark :
- spark-shell --master local[*] --jars KfkProducer/target/scala-2.11/kfk-producer_2.11-1.0.0.jar