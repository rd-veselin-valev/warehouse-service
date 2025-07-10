package com.example.warehouse_service.service.impl;

import com.example.warehouse_service.data.entity.Warehouse;
import com.example.warehouse_service.data.repository.WarehouseRepository;
import com.example.warehouse_service.dto.CreateWarehouseDto;
import com.example.warehouse_service.dto.UpdateWarehouseDto;
import com.example.warehouse_service.dto.WarehouseDto;
import com.example.warehouse_service.mapper.WarehouseMapper;
import com.example.warehouse_service.service.WarehouseService;
import com.example.warehouse_service.util.errormessage.ErrorMessages;
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
        return mapper.toWarehouseDto(getWarehouses(id));
    }

    @Override
    public WarehouseDto createWarehouse(CreateWarehouseDto warehouseDto) {
        var warehouse = mapper.toWarehouse(warehouseDto);
        return mapper.toWarehouseDto(warehouseRepository.save(warehouse));
    }

    @Override
    public WarehouseDto updateWarehouse(int id, UpdateWarehouseDto warehouseDto) {
        var warehouse = getWarehouses(id);
        warehouse.setWarehouseName(warehouseDto.getWarehouseName());
        warehouse.setWarehouseIdentifier(warehouseDto.getWarehouseIdentifier());

        return mapper.toWarehouseDto(warehouseRepository.save(warehouse));
    }

    @Override
    public void deleteWarehouse(int id) {
        if (!warehouseRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format(ErrorMessages.WAREHOUSE_NOT_FOUND, id));
        }

        warehouseRepository.deleteById(id);
    }

    private Warehouse getWarehouses(int id) {
        return warehouseRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ErrorMessages.WAREHOUSE_NOT_FOUND, id)));
    }
}
