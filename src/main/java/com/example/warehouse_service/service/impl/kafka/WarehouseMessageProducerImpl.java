package com.example.warehouse_service.service.impl.kafka;

import com.example.warehouse_service.dto.WarehouseDto;
import com.example.warehouse_service.message.Message;
import com.example.warehouse_service.service.WarehouseMessageProducer;
import com.example.warehouse_service.util.kafka.KafkaTopics;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WarehouseMessageProducerImpl implements WarehouseMessageProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void sentMessage(String key, Message<WarehouseDto> message) {
        try {
            kafkaTemplate.send(KafkaTopics.WAREHOUSES_TOPIC, key, objectMapper.writeValueAsString(message)).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Kafka send interrupted", e);
        } catch (Exception e) {
            log.error("Kafka send failed", e);
        }
    }
}
