package com.example.warehouse_service.service;

import com.example.warehouse_service.dto.CreateWarehouseDto;
import com.example.warehouse_service.dto.UpdateWarehouseDto;
import com.example.warehouse_service.dto.WarehouseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WarehouseService {
    List<WarehouseDto> getWarehouses(Pageable pageable);

    WarehouseDto getWarehouseById(int id);

    WarehouseDto createWarehouse(@Valid CreateWarehouseDto warehouseDto);

    WarehouseDto updateWarehouse(@Valid UpdateWarehouseDto warehouseDto);

    void deleteWarehouse(int id);
}
