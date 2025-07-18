package com.example.warehouse_service.config.kafka;

import com.example.warehouse_service.util.kafka.KafkaSchemas;
import com.example.warehouse_service.util.kafka.KafkaTopics;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessageValidationConfig {
    @Bean
    public Map<String, Schema> schemas() {
        var schemaMap = new HashMap<String, Schema>();
        schemaMap.put(KafkaTopics.WAREHOUSES_TOPIC, loadSchema(KafkaSchemas.WAREHOUSE));

        return schemaMap;
    }

    private Schema loadSchema(String path) {
        var schemaStream = getClass().getClassLoader().getResourceAsStream(path);
        if (schemaStream == null) {
            throw new IllegalArgumentException("Schema not found at: " + path);
        }
        JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));
        return SchemaLoader.load(rawSchema);
    }
}
