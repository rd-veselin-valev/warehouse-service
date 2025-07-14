package com.example.warehouse_service.service;

import com.example.warehouse_service.dto.WarehouseDto;
import com.example.warehouse_service.message.Message;

public interface WarehouseMessageProducer {
    void sentMessage(String key, Message<WarehouseDto> Message);
}
