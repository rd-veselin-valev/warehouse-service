package com.example.warehouse_service.service.impl;

import com.example.warehouse_service.data.repository.WarehouseRepository;
import com.example.warehouse_service.dto.CreateWarehouseDto;
import com.example.warehouse_service.dto.UpdateWarehouseDto;
import com.example.warehouse_service.dto.WarehouseDto;
import com.example.warehouse_service.mapper.WarehouseMapper;
import com.example.warehouse_service.service.WarehouseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper mapper;

    @Override
    public List<WarehouseDto> getWarehouses(Pageable pageable) {
        return warehouseRepository.findAll(pageable)
                .stream()
                .map(mapper::toWarehouseDto)
                .toList();
    }

    @Override
    public WarehouseDto getWarehouseById(int id) {
        var warehouse = warehouseRepository.findById(id);

        if (warehouse.isEmpty()) {
            throw new EntityNotFoundException(String.format("Warehouse with id %d not found", id));
        }

        return mapper.toWarehouseDto(warehouse.get());
    }

    @Override
    public WarehouseDto createWarehouse(CreateWarehouseDto warehouseDto) {
        var warehouse = mapper.toWarehouse(warehouseDto);
        return mapper.toWarehouseDto(warehouseRepository.save(warehouse));
    }

    @Override
    public WarehouseDto updateWarehouse(UpdateWarehouseDto warehouseDto) {
        var warehouse = warehouseRepository.findById(warehouseDto.getId());

        if (warehouse.isEmpty()) {
            throw new EntityNotFoundException(String.format("Warehouse with id %d not found", warehouseDto.getId()));
        }

        var existingWarehouse = warehouse.get();
            existingWarehouse.setWarehouseName(warehouseDto.getWarehouseName());
            existingWarehouse.setWarehouseIdentifier(warehouseDto.getWarehouseIdentifier());

        var savedCustomer = warehouseRepository.save(existingWarehouse);
        return mapper.toWarehouseDto(savedCustomer);
    }

    @Override
    public void deleteWarehouse(int id) {
        if (!warehouseRepository.existsById(id)) {
            throw new EntityNotFoundException("Warehouse not found");
        }

        warehouseRepository.deleteById(id);
    }
}
