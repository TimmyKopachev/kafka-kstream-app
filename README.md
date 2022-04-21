# Kafka Streams Examples

---

> This project contains code example that demonstrates how to implement real-time applications and event-driven microservices using the Streams API of Apache Kafka</p>

<h2>Getting Started</h2>

___

There are 2 ways to run an application:

1) Use ***local*** profile for _main-screen_ service which enables a creation of Embedded kafka & required topics
2) Use ***docker-compose.yml*** file to run zookeeper & kafka containers

___

<h6> Please run 'main-screen' and 'lobby-handler' services to check the application.
The result should be displayed in console</h6>


* <h5> 1. Running the application with 'local' profile works by default : </h5> 
* <h5> 2. Running the application via Docker in a nutshell: </h5> 

```
run docker containers:

    - docker-compose up -d

check everything is running:    
    
    - docker ps
    
enter to kafka container:

    - docker exec -it kafka /bin/sh
    
enter to kafka topic creation:

    - cd /opt/
    - ls
    - cd kafka_{version}

    - ./bin/kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 2 --topic RANKED-MATCHMAKING-LOBBY-EVENT_TOPIC

    - ./bin/kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 2 --topic BASIC-MATCHMAKING-LOBBY-EVENT_TOPIC
    
    - ./bin/kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic APPROVE-MATCHMAKING-FOUND_TOPIC
    
check the list of existing topi:

    - ./bin/kafka-topics.sh --list --zookeeper zookeeper    
    
check output of kafka topic already created:

    - ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic external-output-notificaiton --from-beginning
    
```

___ 

Where to find help
---

* Looking for documentation on Apache Kafka's Streams API?
    * recommending to read [the Kafka Streams chapter](https://docs.confluent.io/platform/current/streams/index.html) in
      the [Confluent Platform documentation](https://docs.confluent.io/home/overview.html).
    * Watch our talk [Rethinking Stream Processing with Apache Kafka](https://www.youtube.com/watch?v=ACwnrnVJXuE)

___
`author` **Dzmitry Kapachou**
