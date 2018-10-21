package com.example.mykafka.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Process data from  one or more Kafka topics (topics provided as csv string).
 * @author Aniket Pandit
 *
 */

@Service
public class ConsumerService {

	@Autowired
	Properties props;

	@Value("${bootstrap-servers}")
	private String bootstrapServers;
	@Value("${group-id}")
	private String groupId;
	@Value("${enable-auto-commit}")
	private String enableAutoCommit;
	@Value("${auto-commit-interval-ms}")
	private String autoCommitIntervalMs;
	@Value("${key-deserializer}")
	private String keyDeserializer;
	@Value("${value-deserializer}")
	private String valueDeserializer;

	@PostConstruct
	private void init() {
		props.put("bootstrap.servers", bootstrapServers);
		props.put("group.id", groupId);
		props.put("enable.auto.commit", enableAutoCommit);
		props.put("auto.commit.interval.ms", autoCommitIntervalMs);
		props.put("key.deserializer", keyDeserializer);
		props.put("value.deserializer", valueDeserializer);
	}

	public void consume(String cTopic[]) {
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		List<String> asList = Arrays.asList(cTopic);
		consumer.subscribe(asList);
		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
				for (ConsumerRecord<String, String> record : records)
					System.out.println(record.value());
			}
		} finally {
			consumer.close();
		}

	}

}
