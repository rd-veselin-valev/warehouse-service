package com.example.warehouse_service.service.impl;

import com.example.warehouse_service.data.entity.Warehouse;
import com.example.warehouse_service.data.repository.WarehouseRepository;
import com.example.warehouse_service.dto.CreateWarehouseDto;
import com.example.warehouse_service.dto.UpdateWarehouseDto;
import com.example.warehouse_service.dto.WarehouseDto;
import com.example.warehouse_service.mapper.WarehouseMapper;
import com.example.warehouse_service.message.Message;
import com.example.warehouse_service.message.enums.ActionType;
import com.example.warehouse_service.service.WarehouseMessageProducer;
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
    private final WarehouseMessageProducer warehouseMessageProducer;

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
    public WarehouseDto createWarehouse(CreateWarehouseDto createWarehouseDto) {
        var warehouse = mapper.toWarehouse(createWarehouseDto);
        var savedWarehouse = warehouseRepository.save(warehouse);
        var warehouseDto = mapper.toWarehouseDto(savedWarehouse);
        var payload = new Message<>(ActionType.CREATE, warehouseDto);

        warehouseMessageProducer.sentMessage(String.valueOf(savedWarehouse.getId()), payload);

        return warehouseDto;
    }

    @Override
    public WarehouseDto updateWarehouse(int id, UpdateWarehouseDto updateWarehouseDto) {
        var warehouse = getWarehouses(id);
        warehouse.setWarehouseName(updateWarehouseDto.getWarehouseName());
        warehouse.setWarehouseIdentifier(updateWarehouseDto.getWarehouseIdentifier());
        var updatedWarehouse = warehouseRepository.save(warehouse);
        var warehouseDto = mapper.toWarehouseDto(updatedWarehouse);
        var payload = new Message<>(ActionType.UPDATE, warehouseDto);

        warehouseMessageProducer.sentMessage(String.valueOf(updatedWarehouse.getId()), payload);

        return warehouseDto;
    }

    @Override
    public void deleteWarehouse(int id) {
        if (!warehouseRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format(ErrorMessages.WAREHOUSE_NOT_FOUND, id));
        }

        warehouseRepository.deleteById(id);
        var payload = new Message<>(ActionType.UPDATE, (WarehouseDto) null);

        warehouseMessageProducer.sentMessage(String.valueOf(id), payload);
    }

    private Warehouse getWarehouses(int id) {
        return warehouseRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ErrorMessages.WAREHOUSE_NOT_FOUND, id)));
    }
}
