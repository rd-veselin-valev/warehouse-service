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
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name can't exceed 50 characters")
    private String name;

    @NotBlank(message = "Identifier is required")
    @Size(max = 16, message = "Identifier can't exceed 16 characters")
    private String identifier;
}
