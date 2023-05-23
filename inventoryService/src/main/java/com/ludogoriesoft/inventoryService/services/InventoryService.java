package com.ludogoriesoft.inventoryService.services;

import com.ludogoriesoft.inventoryService.dto.InventoryDTO;
import com.ludogoriesoft.inventoryService.entities.Inventory;
import com.ludogoriesoft.inventoryService.repositories.InventoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    public InventoryDTO inventoryToInventoryDTO(Inventory inventory){
        return modelMapper.map(inventory, InventoryDTO.class);
    }
    public List<InventoryDTO> getAllInventories() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories
                .stream()
                .map(this::inventoryToInventoryDTO)
                .collect(Collectors.toList());
    }
}
