## Start Swarm cluster

NOTE : - build ubspkcli_kfk_img from spark_client_yarn_cluster_dockerized repository

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

3. start kafka nodes 
```shell
$ docker stack deploy -c docker-compose.yml kfk
$ docker service ls
ID             NAME          MODE         REPLICAS   IMAGE                             PORTS
ei36xmjsdg5d   kfk_kfk1      replicated   1/1        mkenjis/ubkfk_img:latest          *:9092->9092/tcp
xcdrvykgjbm9   kfk_kfk2      replicated   1/1        mkenjis/ubkfk_img:latest          *:9093->9092/tcp
ozoy9323v4q4   kfk_kfk3      replicated   1/1        mkenjis/ubkfk_img:latest          *:9094->9092/tcp
02n938f1zdy9   kfk_spk_cli   replicated   1/1        mkenjis/ubspkcli_kfk_img:latest   *:8082->8082/tcp
```

4. access a kafka node
```shell
$ docker container ls   # run it in any node and check which <container ID>
CONTAINER ID   IMAGE                             COMMAND                  CREATED          STATUS          PORTS                                          NAMES
ac0fbc8e4990   mkenjis/ubspkcli_kfk_img:latest   "/usr/bin/supervisord"   30 seconds ago   Up 29 seconds   4040/tcp, 7077/tcp, 8080-8082/tcp, 10000/tcp   kfk_spk_cli.1.v5g31acoi6cu87oolhy7tuwpq
d63037348106   mkenjis/ubkfk_img:latest          "/usr/bin/supervisord"   44 seconds ago   Up 43 seconds   9092/tcp                                       kfk_kfk3.1.pz35y6xb7a9fjxm95xzrsc8z5
29ca9a0f42f6   mkenjis/ubkfk_img:latest          "/usr/bin/supervisord"   48 seconds ago   Up 47 seconds   9092/tcp                                       kfk_kfk2.1.l0bgrgnwsf14aygf8ycso8qol
5ae363118b06   mkenjis/ubkfk_img:latest          "/usr/bin/supervisord"   52 seconds ago   Up 50 seconds   9092/tcp                                       kfk_kfk1.1.yj4eimhhr73ljox98wrto0qec

$ docker container exec -it <kfk ID> bash
```

5. check zookeeper service
```shell
$ zookeeper-shell.sh kfk1:2181 ls /brokers/ids
Connecting to kfk1:2181

WATCHER::

WatchedEvent state:SyncConnected type:None path:null
[0, 1, 2]
```

6. create a topic in kafka
```shell
$ kafka-topics.sh --create --zookeeper kfk1:2181,kfk2:2181,kfk3:2181 --replication-factor 1 --partitions 1 --topic test
Created topic "test".

$ kafka-topics.sh --list --zookeeper kfk1:2181,kfk2:2181,kfk3:2181
test
```

7. copy orders.txt to spark client node
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

8. start spark-shell loading kakfa jar files
```shell
spark-shell --master local[*] --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.3.2,org.apache.spark:spark-streaming-kafka-0-10_2.11:2.3.2
```

