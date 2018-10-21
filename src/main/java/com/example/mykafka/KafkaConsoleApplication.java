package com.example.mykafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.mykafka.service.AdminService;
import com.example.mykafka.service.ConsumerService;
import com.example.mykafka.service.ProducerService;

/**
   KafkaConsoleApplication  functionalities...
   1. producer : On running instance of producer allows an application to publish to one Kafka topic.
   2. consumer : On running instance of consumer allows an application to process data from  one or more Kafka topics (topics provided as csv string).
   3. listTopics : List topics in a Kafka broker.
   @author Aniket Pandit
 *
 */

@SpringBootApplication
public class KafkaConsoleApplication implements CommandLineRunner {

	private Logger LOG = LoggerFactory.getLogger(KafkaConsoleApplication.class);

	@Autowired
	private ProducerService pService;

	@Autowired
	ConsumerService cService;

	@Autowired
	AdminService adminService;

	@Value("${topic}")
	private String topic;

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsoleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		final String validArgMsg = "Provide valid argument: 'consumer | producer | listTopics'";
		if (args == null || args.length == 0) {
			LOG.error(validArgMsg);
			return;
		}
		if (topic == null || topic.isEmpty()) {
			LOG.error("No topic specified");
		}
		String topics[] = topic.split(",");

		switch (args[0]) {
		case "consumer":
			LOG.info("Starting Consumer, consuming from topics" + topics);
			adminService.listTopics();
			cService.consume(topics);
			break;
		case "producer":
			LOG.info("Starting Producer, producing to topic [ " + topics[0] + " ]");
			adminService.listTopics();
			pService.produce(topics[0]);
			break;
		case "listTopics":
			adminService.listTopics();
			break;
		default:
			LOG.error(validArgMsg);
			break;
		}

	}
}
