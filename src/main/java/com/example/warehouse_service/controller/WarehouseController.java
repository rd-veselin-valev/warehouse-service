package com.example.warehouse_service.controller;

import com.example.warehouse_service.dto.CreateWarehouseDto;
import com.example.warehouse_service.dto.UpdateWarehouseDto;
import com.example.warehouse_service.dto.WarehouseDto;
import com.example.warehouse_service.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<WarehouseDto>> getWarehouses() {
        return ResponseEntity.ok(warehouseService.getWarehouses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDto> getWarehouseById(@PathVariable int id) {
        var warehouse = warehouseService.getWarehouseById(id);
        return warehouse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
        return warehouse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable int id) {
        return warehouseService.deleteWarehouse(id) ?
                ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
