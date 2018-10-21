package com.example.mykafka.service;

import java.util.Properties;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Producer service produces records to Kafka topic
 * 
 * @author Aniket Pandit
 *
 */
@Service
public class ProducerService {

	private Logger LOG = LoggerFactory.getLogger(ProducerService.class);

	@Autowired
	Properties props;
	
	@Value("${bootstrap-servers}")
	private String bootstrapServers;
	@Value("${acks}")
	private String acks;
	@Value("${retries}")
	private String retries;
	@Value("${batch-size}")
	private String batchSize;
	@Value("${linger-ms}")
	private String lingerMs;
	@Value("${buffer-memory}")
	private String bufferMemory;
	@Value("${key-serializer}")
	private String keySerializer;
	@Value("${value-serializer}")
	private String valueSerializer;
	@Value("${topic}")
	private String topic;

	@PostConstruct
	private void init() {
		props.put("bootstrap.servers", bootstrapServers);
		props.put("acks", acks);
		props.put("retries", retries);
		props.put("batch.size", batchSize);
		props.put("linger.ms", lingerMs);
		props.put("buffer.memory", bufferMemory);
		props.put("key.serializer", keySerializer);
		props.put("value.serializer", valueSerializer);
	}

	public void produce() {

		int i = 0;
		Scanner in = new Scanner(System.in);
		Producer<String, String> producer = new KafkaProducer<>(props);
		System.out.println("Enter messages, to quit type 'exit'......");
		String line = "";
		try {
			do {
				line = in.nextLine();
				producer.send(new ProducerRecord<String, String>(topic, Integer.toString(i), line));
				i++;
			} while (!line.equals("exit"));
		} finally {
			in.close();
			producer.close();
		}
	}
}
