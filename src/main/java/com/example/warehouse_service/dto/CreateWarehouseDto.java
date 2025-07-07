package com.example.warehouse_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWarehouseDto {
    @NotBlank(message = "WarehouseName is required")
    @Size(max = 50, message = "WarehouseName can't exceed 50 characters")
    private String warehouseName;

    @NotBlank(message = "WarehouseIdentifier is required")
    @Size(max = 16, message = "WarehouseIdentifier can't exceed 16 characters")
    private String warehouseIdentifier;
}
