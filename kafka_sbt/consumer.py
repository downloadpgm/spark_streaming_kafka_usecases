# pip install kafka-python

from kafka import KafkaConsumer

consumer = KafkaConsumer('result', bootstrap_servers='kfk1:9092,kfk2:9092,kfk3:9092')

for message in consumer:
    print (message.value)