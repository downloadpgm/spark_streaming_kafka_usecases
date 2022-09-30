## Start Swarm cluster

NOTE : follow steps below to build spark with python-kafka library

       - build ubspkcli_kfk_img from https://github.com/downloadpgm/spark_standalone_cluster_dockerized

       - in Dockerfile, add :
	   
	     RUN apt-get install -y python3-pip && pip install kafka-python
		 
	   - docker image build -t mkenjis/ubspkcli_kfk_img .

1. start swarm mode in node1
```shell
$ docker swarm init --advertise-addr <IP node1>
$ docker swarm join-token manager  # issue a token to add a node as manager to swarm
```

2. add more managers in swarm cluster (node2, node3, ...)
```shell
$ docker swarm join --token <token> <IP nodeN>:2377
```

3. start kafka brokers and spark client node
```shell
$ docker stack deploy -c docker-compose.yml kfk
$ docker service ls
ID             NAME          MODE         REPLICAS   IMAGE                             PORTS
ei36xmjsdg5d   kfk_kfk1      replicated   1/1        mkenjis/ubkfk_img:latest          *:9092->9092/tcp
xcdrvykgjbm9   kfk_kfk2      replicated   1/1        mkenjis/ubkfk_img:latest          *:9093->9092/tcp
ozoy9323v4q4   kfk_kfk3      replicated   1/1        mkenjis/ubkfk_img:latest          *:9094->9092/tcp
02n938f1zdy9   kfk_spk_cli   replicated   1/1        mkenjis/ubspkcli_kfk_img:latest   *:8082->8082/tcp
```

4. configure kafka cluster https://github.com/downloadpgm/kafka_cluster_dockerized

5. copy orders.txt to spark client node
```shell
$ docker container cp orders.txt <spkcli ID>:/root

$ docker container exec -it <spkcli ID> bash

$ ls -l 
-rwxr-xr-x 1 root root      156 Sep 25 12:17 create_conf_files.sh
-rw-rw-r-- 1 1000 1000 22817161 Sep 25 11:59 orders.txt
-rwxr-xr-x 1 root root      974 Sep 25 12:17 run_spark.sh
-rwxr-xr-x 1 root root      409 Sep 25 12:17 stop_spark.sh
test
```

6. start spark-shell loading kakfa jar files
```shell
spark-shell --master local[*] --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.3.2,org.apache.spark:spark-streaming-kafka-0-10_2.11:2.3.2
```

