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
    @NotBlank(message = "Warehouse_Name is required")
    @Size(max = 50, message = "warehouse_ can't exceed 50 characters")
    private String warehouse_name;

    @NotBlank(message = "Warehouse_identifier is required")
    @Size(max = 16, message = "Warehouse_identifier can't exceed 16 characters")
    private String warehouse_identifier;
}
