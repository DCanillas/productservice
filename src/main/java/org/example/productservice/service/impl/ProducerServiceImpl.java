package org.example.productservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.LogMongoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProducerServiceImpl {
    public static final String topic = "orders";

    private final KafkaTemplate<String, LogMongoDTO> kafkaTemplate;

    @Autowired
    public ProducerServiceImpl(KafkaTemplate<String, LogMongoDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishToTopic(LogMongoDTO message){
        log.info("Publishing: "+message);
        this.kafkaTemplate.send(topic, message);
    }
}
