# pip install kafka-python

from kafka import KafkaProducer

producer = KafkaProducer(bootstrap_servers='kfk1:9092,kfk2:9092,kfk3:9092')

producer.send('test', b'Hello, World!')

producer.send('test', key=b'message-two', value=b'This is Kafka-Python')