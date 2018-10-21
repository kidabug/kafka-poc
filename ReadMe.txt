ReadeME.txt
------------------
   Instruction to run this Kafka-poc. The Poc supports follwing  functionalities...
   1. producer : On running instance of producer allows an application to publish to one Kafka topic.
   2. consumer : On running instance of consumer allows an application to process data from  one or more Kafka topics (topics provided as csv string).
   3. listTopics : List topics in a Kafka broker.

Quick start to run this application...

1.Download Kafka 2.0.0 release and un-tar it.
> tar -xzf kafka_2.11-2.0.0.tgz
> cd kafka_2.11-2.0.0

2.Start kafka single-node ZooKeeper instance...
> bin/zookeeper-server-start.sh config/zookeeper.properties

3.Start the Kafka server
> bin/kafka-server-start.sh config/server.properties

4.Create Kafka topics using Kafka-topic binaries in the Kafka installation.
> bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic <topic-name>

 List topics using..
 > bin/kafka-topics.sh --list --zookeeper localhost:2181 

5. Download  the Kafka-Poc project 'https://github.com/kidabug/kafka-poc'. Make sure Java and Maven are configured.
6. Do mvn build for the project...
> mvn clean install

7.List topics using Kafka-poc
>  java -jar target/kafka-poc-0.0.1-SNAPSHOT.jar listTopics --bootstrap-servers=localhost:9092

8.On One terminal windows  run a producer using command
>  java -jar target/kafka-poc-0.0.1-SNAPSHOT.jar producer --topic=<topic-name> --bootstrap-servers=localhost:9092

    Similarly other producer can be started in different terminals using different topics. The default value
    for 'bootstrap-servers' is localhost:9092. This attribute can be overriden from command line.

9.Open different terminal to start consumer which can listen to multiple topics (<topic-name> provided as csv string)...
>  java -jar target/kafka-poc-0.0.1-SNAPSHOT.jar consumer --topic=<topic-name> --bootstrap-servers=localhost:9092

    User should be able to publish on topics in producer terminal and text published in producer terminal will be displayed on consumer terminal.

10. To reset the topic to initial state use below kafka-configs binary to change the retention policy to 1 sec, this will reset the topic in 1 second.
> bin/kafka-configs.sh --zookeeper localhost:2181 --alter --entity-type topics --entity-name test --add-config 'retention.ms=1000'

11. If you want to delete the topic, first set attribute in kafka config server.properties, 'delete.topic.enable=true'  and then fire command ...
> bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic <topic-name>
