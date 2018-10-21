package com.example.mykafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.mykafka.service.AdminService;
import com.example.mykafka.service.ConsumerService;
import com.example.mykafka.service.ProducerService;

/**
 * 
 * @author Aniket Pandit
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

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsoleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		final String validArgMsg = "Provide valid argument: 'producer | consumer'";
		if (args == null || args.length == 0) {
			LOG.error(validArgMsg);
			return;
		}

		switch (args[0]) {
		case "consumer":
			LOG.info("Starting Consumer....");
			adminService.listTopics();
			cService.consume();
			break;
		case "producer":
			LOG.info("Starting Producer....");
			adminService.listTopics();
			pService.produce();
			break;
		default:
			LOG.error(validArgMsg);
			break;
		}

	}
}
