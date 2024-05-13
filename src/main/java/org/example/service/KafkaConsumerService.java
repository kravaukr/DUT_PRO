package org.example.service;

import org.example.model.User;
import org.example.repository.DataRepo;
import org.slf4j.Logger;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

public class KafkaConsumerService {

    private static final Logger log = getLogger(KafkaConsumerService.class);

    private final KafkaConsumer<String, String> consumer;

    private DataRepo dataRepo;

    public KafkaConsumerService(String bootstrapServers, String groupId, String topic, DataRepo dataRepo) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", groupId);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        this.consumer = new KafkaConsumer<>(props);
        this.consumer.subscribe(Collections.singletonList(topic));
        this.dataRepo = dataRepo;
    }

    public void consumeMessages() {
        ObjectMapper objectMapper = new ObjectMapper();

        while (true) {
            // Отримуємо повідомлення з Kafka
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                log.info("Received record from Kafka: " + record.value());

                try {
                    // Десеріалізуємо JSON у об'єкт User
                    User user = objectMapper.readValue(record.value(), User.class);

                    // Створюємо користувача в базі даних
                    boolean result = dataRepo.createUser(user);
                    if (result) {
                        log.info("Successfully created user: " + user);
                    } else {
                        log.error("Failed to create user: " + user);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
