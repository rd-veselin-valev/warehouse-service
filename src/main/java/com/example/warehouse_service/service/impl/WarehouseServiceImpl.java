package com.example.warehouse_service.service.impl;

import com.example.warehouse_service.data.repository.WarehouseRepository;
import com.example.warehouse_service.dto.CreateWarehouseDto;
import com.example.warehouse_service.dto.UpdateWarehouseDto;
import com.example.warehouse_service.dto.WarehouseDto;
import com.example.warehouse_service.mapper.WarehouseMapper;
import com.example.warehouse_service.service.DateTimeService;
import com.example.warehouse_service.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final DateTimeService dateTimeService;
    private final WarehouseMapper mapper;

    @Override
    public List<WarehouseDto> getWarehouses() {
        return warehouseRepository.findAll()
                .stream()
                .map(mapper::toWarehouseDto)
                .toList();
    }

    @Override
    public Optional<WarehouseDto> getWarehouseById(int id) {
        return warehouseRepository.findById(id)
                .map(mapper::toWarehouseDto);
    }

    @Override
    public WarehouseDto createWarehouse(CreateWarehouseDto warehouseDto) {
        var warehouse = mapper.toWarehouse(warehouseDto);
        var dateNow = dateTimeService.now();
        warehouse.setCreated(dateNow);
        warehouse.setUpdated(dateNow);

        return mapper.toWarehouseDto(warehouseRepository.save(warehouse));
    }

    @Override
    public Optional<WarehouseDto> updateWarehouse(UpdateWarehouseDto warehouseDto) {
        return warehouseRepository.findById(warehouseDto.getId())
                .map(existingWarehouse -> {
                    existingWarehouse.setWarehouse_name(warehouseDto.getWarehouse_name());
                    existingWarehouse.setWarehouse_identifier(warehouseDto.getWarehouse_identifier());
                    existingWarehouse.setUpdated(dateTimeService.now());

                    var savedCustomer = warehouseRepository.save(existingWarehouse);
                    return mapper.toWarehouseDto(savedCustomer);
                });
    }

    @Override
    public boolean deleteWarehouse(int id) {
        if (!warehouseRepository.existsById(id)) {
            return false;
        }

        warehouseRepository.deleteById(id);
        return true;
    }
}
