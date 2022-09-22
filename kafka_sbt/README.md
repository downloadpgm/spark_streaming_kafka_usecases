# kafka sbt

To run, prepare a Spark environment having YARN/Standalone cluster manager

$ docker stack deploy -c docker-composer.yml kfk

1) download sbt

```shell
<SPK>
$ wget https://github.com/sbt/sbt/releases/download/v1.3.8/sbt-1.3.8.tgz
$ tar zxvf sbt-1.3.8.tgz
$ mv sbt /usr/local
$ export PATH=$PATH:/usr/local/sbt/bin
```

2) run sbt to prepare enviroment

```shell
<SPK>
$ sbt
```

3) create directory for build

```shell
<SPK>
$ mkdir app
$ cd app
$ # copy kafka-consumer.sbt and KafkaStream.scala
```

4) build and create jar
```shell
<SPK>
$ sbt package
$ cd ~
```

5) create test topic and run kafka producer script
```shell
<KFK>
$ kafka-topics.sh --create --zookeeper kfk1:2181,kfk2:2181,kfk3:2181 --replication-factor 2 --partitions 1 --topic test
Created topic "test".
$ kafka-topics.sh --list --zookeeper kfk1:2181,kfk2:2181,kfk3:2181
test
$ kafka-console-producer.sh --broker-list kfk1:9092,kfk2:9092,kfk3:9092 --topic test
>Apache Spark is a multi-language engine
>Spark is an Open Source
>optimized for businesses and organizations.
>Create interactive augmented reality experiences
>

```

6) run the package

```shell
<SPK>
$ spark-submit --master local[*] --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.3.2,org.apache.spark:spark-streaming-kafka-0-10_2.11:2.3.2 --class KafkaStream.kafkastream app/target/scala-2.11/kafka-consumer_2.11-1.0.0.jar
Ivy Default Cache set to: /root/.ivy2/cache
The jars for the packages stored in: /root/.ivy2/jars
:: loading settings :: url = jar:file:/usr/local/spark-2.3.2-bin-hadoop2.7/jars/ivy-2.4.0.jar!/org/apache/ivy/core/settings/ivysettings.xml
org.apache.spark#spark-sql-kafka-0-10_2.11 added as a dependency
org.apache.spark#spark-streaming-kafka-0-10_2.11 added as a dependency
:: resolving dependencies :: org.apache.spark#spark-submit-parent-ba332503-3da8-4bc2-a408-8b7de2c782bd;1.0
        confs: [default]
        found org.apache.spark#spark-sql-kafka-0-10_2.11;2.3.2 in central
        found org.apache.kafka#kafka-clients;0.10.0.1 in central
        found net.jpountz.lz4#lz4;1.3.0 in central
        found org.xerial.snappy#snappy-java;1.1.2.6 in central
        found org.slf4j#slf4j-api;1.7.16 in central
        found org.spark-project.spark#unused;1.0.0 in central
        found org.apache.spark#spark-streaming-kafka-0-10_2.11;2.3.2 in central
:: resolution report :: resolve 964ms :: artifacts dl 13ms
        :: modules in use:
        net.jpountz.lz4#lz4;1.3.0 from central in [default]
        org.apache.kafka#kafka-clients;0.10.0.1 from central in [default]
        org.apache.spark#spark-sql-kafka-0-10_2.11;2.3.2 from central in [default]
        org.apache.spark#spark-streaming-kafka-0-10_2.11;2.3.2 from central in [default]
        org.slf4j#slf4j-api;1.7.16 from central in [default]
        org.spark-project.spark#unused;1.0.0 from central in [default]
        org.xerial.snappy#snappy-java;1.1.2.6 from central in [default]
        ---------------------------------------------------------------------
        |                  |            modules            ||   artifacts   |
        |       conf       | number| search|dwnlded|evicted|| number|dwnlded|
        ---------------------------------------------------------------------
        |      default     |   7   |   0   |   0   |   0   ||   7   |   0   |
        ---------------------------------------------------------------------
:: retrieving :: org.apache.spark#spark-submit-parent-ba332503-3da8-4bc2-a408-8b7de2c782bd
        confs: [default]
        0 artifacts copied, 7 already retrieved (0kB/24ms)
22/09/21 19:15:51 WARN NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
-------------------------------------------
Batch: 0
-------------------------------------------
+---+-----+
|key|value|
+---+-----+
+---+-----+

-------------------------------------------
Batch: 1
-------------------------------------------
+----+--------------------+
| key|               value|
+----+--------------------+
|null|Apache Spark is a...|
+----+--------------------+

-------------------------------------------
Batch: 2
-------------------------------------------
+----+--------------------+
| key|               value|
+----+--------------------+
|null|Spark is an Open ...|
+----+--------------------+

-------------------------------------------
Batch: 3
-------------------------------------------
+----+--------------------+
| key|               value|
+----+--------------------+
|null|optimized for bus...|
+----+--------------------+

-------------------------------------------
Batch: 4
-------------------------------------------
+----+--------------------+
| key|               value|
+----+--------------------+
|null|Create interactiv...|
+----+--------------------+


```



