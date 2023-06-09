package com.ludogoriesoft.inventoryService.controllers;

import com.ludogoriesoft.inventoryService.dto.InventoryDTO;
import com.ludogoriesoft.inventoryService.services.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventories")
@AllArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventories() {
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventoryByProductId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryByProductId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> updateInventoryById(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO) {
        return ResponseEntity.ok(inventoryService.updateInventoryByProductId(id, inventoryDTO));
    }
    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventoryDTO, UriComponentsBuilder uriComponentsBuilder) {
        URI location = uriComponentsBuilder.path("/api/v1/inventories/{id}")
                .buildAndExpand(inventoryService.createInventory(inventoryDTO).getProductId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
