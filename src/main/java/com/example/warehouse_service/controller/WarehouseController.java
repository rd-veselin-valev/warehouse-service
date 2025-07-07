package com.example.warehouse_service.controller;

import com.example.warehouse_service.dto.CreateWarehouseDto;
import com.example.warehouse_service.dto.UpdateWarehouseDto;
import com.example.warehouse_service.dto.WarehouseDto;
import com.example.warehouse_service.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<List<WarehouseDto>> getWarehouses(Pageable pageable) {
        return ResponseEntity.ok(warehouseService.getWarehouses(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDto> getWarehouseById(@PathVariable int id) {
        var warehouse = warehouseService.getWarehouseById(id);
        return ResponseEntity.ok(warehouse);
    }

    @PostMapping
    public ResponseEntity<WarehouseDto> createWarehouse(@RequestBody @Valid CreateWarehouseDto WarehouseDto) {
        var warehouse = warehouseService.createWarehouse(WarehouseDto);
        URI location = URI.create("/api/warehouses/" + warehouse.getId());
        return ResponseEntity.created(location).body(warehouse);
    }

    @PutMapping
    public ResponseEntity<WarehouseDto> updateWarehouse(@RequestBody @Valid UpdateWarehouseDto WarehouseDto) {
        var warehouse = warehouseService.updateWarehouse(WarehouseDto);
        return ResponseEntity.ok(warehouse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable int id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }
}
