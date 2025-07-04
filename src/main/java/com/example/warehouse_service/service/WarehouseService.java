package com.example.warehouse_service.service;

import com.example.warehouse_service.dto.CreateWarehouseDto;
import com.example.warehouse_service.dto.UpdateWarehouseDto;
import com.example.warehouse_service.dto.WarehouseDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface WarehouseService {
    List<WarehouseDto> getWarehouses();

    Optional<WarehouseDto> getWarehouseById(int id);

    WarehouseDto createWarehouse(@Valid CreateWarehouseDto warehouseDto);

    Optional<WarehouseDto> updateWarehouse(@Valid UpdateWarehouseDto warehouseDto);

    boolean deleteWarehouse(int id);
}
