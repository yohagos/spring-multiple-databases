package com.yohagos.multiple_databases.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    @Value("${app.mds.metrics}")
    private String METRICS_TOPIC;
    @Value("${app.mds.notifications}")
    private String NOTIFICATIONS_TOPIC;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMetricsMessage(
            String msg
    ) {
        kafkaTemplate.send(METRICS_TOPIC, msg);
    }

    public void sendNotificationsMessage(
            String msg
    ) {
        kafkaTemplate.send(NOTIFICATIONS_TOPIC, msg);
    }
}
