package org.example.productservice.service.producer;

import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.LogMongoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProducerService {
    public static final String TOPIC = "requests";

    private final KafkaTemplate<String, LogMongoDTO> kafkaTemplate;

    @Autowired
    public ProducerService(KafkaTemplate<String, LogMongoDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishToTopic(LogMongoDTO message){
        log.info("Publishing: "+message);
        this.kafkaTemplate.send(TOPIC, message);
    }
}
