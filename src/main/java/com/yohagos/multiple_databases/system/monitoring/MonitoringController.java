package com.yohagos.multiple_databases.system.monitoring;

import com.yohagos.multiple_databases.system.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*@RestController
@RequestMapping("/monitoring")
@RequiredArgsConstructor*/
public class MonitoringController {

    /*private final KafkaProducerService producerService;

    @PostMapping("/kafka")
    public ResponseEntity<String> sendMessage(
            @RequestBody String message
    ) {
        producerService.sendMetricsMessage(message);
        producerService.sendNotificationsMessage("new message in metrics queue");
        return ResponseEntity.ok("Message queued successfully");
    }*/
}
