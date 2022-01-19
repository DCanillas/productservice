package org.example.productservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.MessageKafkaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProducerServiceImpl {
    public static final String topic = "orders";

    private final KafkaTemplate<String, MessageKafkaDTO> kafkaTemplate;

    @Autowired
    public ProducerServiceImpl(KafkaTemplate<String, MessageKafkaDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishToTopic(MessageKafkaDTO message){
        log.info("Publishing: "+message);
        this.kafkaTemplate.send(topic, message);
    }
}
