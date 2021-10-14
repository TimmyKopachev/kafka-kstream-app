#Kafka Streams Examples

---

>This project contains code example that demonstrates how to implement real-time applications and event-driven microservices using the Streams API of Apache Kafka</p>

<h2>Getting Started</h2>

___

In a nutshell:

* ##### _run docker containers_
```
docker-compose up -d
```
* ##### _check everything is running_
```
docker ps
```

* ##### _enter to kafka container_
```
docker exec -it kafka /bin/sh
```

* ##### _enter to kafka topic creation_
```
cd /opt/

ls

cd kafka_{version}

./bin/kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic game-event-topic

./bin/kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic external-output-notificaiton

```

 ##### _check the list of existing topic_

```
./bin/kafka-topics.sh --list --zookeeper zookeeper

```

##### _check output of kafka topic already created_

```
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic external-output-notificaiton --from-beginning

```


##_Use following endpoint to trigger_ [the application](http://localhost:8082/publish ):
___ 

 Where to find help
---

* Looking for documentation on Apache Kafka's Streams API?
  * recommending to read [the Kafka Streams chapter](https://docs.confluent.io/platform/current/streams/index.html) in the [Confluent Platform documentation](https://docs.confluent.io/home/overview.html).
  *  Watch our talk [Rethinking Stream Processing with Apache Kafka](https://www.youtube.com/watch?v=ACwnrnVJXuE)
___
`author Dzmitry Kapachou`
