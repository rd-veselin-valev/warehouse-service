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
    @NotBlank()
    @Size(max = 50)
    private String warehouseName;

    @NotBlank()
    @Size(max = 16)
    private String warehouseIdentifier;
}
