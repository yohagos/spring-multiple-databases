package com.yohagos.multiple_databases.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.yohagos.multiple_databases.utils.Constants.MDS_METRICS;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = MDS_METRICS)
    public void metricsConsumer(
            String message
    ) {
        messagingTemplate.convertAndSend("/topic/"+MDS_METRICS, message);
    }
}
