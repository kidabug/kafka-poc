package com.example.mykafka.service;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Admin task, like list topic and helps to check broker state
 * 
 * @author Aniket Pandit
 *
 */
@Service
public class AdminService {

	@Autowired
	Properties props;
	@Value("${bootstrap-servers}")
	private String bootstrapServers;
	@Value("${connections-max-idle-ms}")
	private String connectionsMaxIdleMs;
	@Value("${request-timeout-ms}")
	private String requestTimeoutMs;

	private Logger LOG = LoggerFactory.getLogger(AdminService.class);

	@PostConstruct
	private void init() {
		props.put("bootstrap.servers", bootstrapServers);
		props.put("connections.max.idle.ms", connectionsMaxIdleMs);
		props.put("request.timeout.ms", requestTimeoutMs);
	}

	public void listTopics() {
		Set<String> topics = getTopics();
		if (topics != null && !topics.isEmpty()) {
			System.out.println("---------------------- ");
			System.out.println("List of topics:- ");
			System.out.println("---------------------- ");
			for (String topic : topics) {
				System.out.println(topic);
			}
			System.out.println("---------------------- ");
		} else {
			LOG.error("No topics found..........");
		}
	}

	private Set<String> getTopics() {

		Set<String> names = null;
		try (AdminClient client = KafkaAdminClient.create(props)) {
			ListTopicsResult topics = client.listTopics();
			names = topics.names().get();
		} catch (InterruptedException | ExecutionException e) {
			LOG.error("Kafka broker not available");
			throw new RuntimeException(e);
		}
		return names;
	}

}
