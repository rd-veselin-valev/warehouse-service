package com.example.warehouse_service.mapper;

import com.example.warehouse_service.data.entity.Warehouse;
import com.example.warehouse_service.dto.CreateWarehouseDto;
import com.example.warehouse_service.dto.WarehouseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WarehouseMapper {
    WarehouseDto toWarehouseDto(Warehouse warehouse);
    Warehouse toWarehouse(CreateWarehouseDto warehouseDto);
}