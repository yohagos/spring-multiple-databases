package com.yohagos.multiple_databases.configs;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Autowired
    KafkaAdmin kafkaAdmin;

    @PostConstruct
    public void init() {
        kafkaAdmin.initialize();
    }

    /*@Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        return new KafkaAdmin(configs);
    }*/

    @Bean
    @Profile({"Producer"})
    public NewTopic metricsTopic() {
        return TopicBuilder
                .name("mds-metrics")
                .partitions(2)
                .replicas(1)
                .build();
    }


    @Bean
    public NewTopic notificationTopic() {
        return TopicBuilder
                .name("mds-notifications")
                .partitions(2)
                .replicas(1)
                .build();
    }
}
