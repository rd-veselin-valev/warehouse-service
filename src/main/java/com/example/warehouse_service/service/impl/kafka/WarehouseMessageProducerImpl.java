package com.example.warehouse_service.service.impl.kafka;

import com.example.warehouse_service.data.entity.ProducedMessage;
import com.example.warehouse_service.data.enums.MessageStatus;
import com.example.warehouse_service.data.repository.ProducedMessageRepository;
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
    private final ProducedMessageRepository producedMessageRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void sentMessage(String key, Message<WarehouseDto> message) {
        log.info("Sending message with key {} to topic {}", key, KafkaTopics.WAREHOUSES_TOPIC);
        try {
            var payload = objectMapper.writeValueAsString(message);
            kafkaTemplate.send(KafkaTopics.WAREHOUSES_TOPIC, key, payload).get();
            saveProducedMessage(key, message, MessageStatus.SENT);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Kafka send interrupted", e);
            saveProducedMessage(key, message, MessageStatus.FAILED);
        } catch (Exception e) {
            log.error("Kafka send failed", e);
            saveProducedMessage(key, message, MessageStatus.FAILED);
        }
    }

    private void saveProducedMessage(String key, Message<WarehouseDto> message, MessageStatus status) {
        var producedMessage = ProducedMessage.builder()
                .msgKey(key)
                .payload(message.toString())
                .status(status)
                .build();

        producedMessageRepository.save(producedMessage);
    }
}
