package com.ludogoriesoft.inventoryService.services;

import com.ludogoriesoft.inventoryService.config.ProductServiceClient;
import com.ludogoriesoft.inventoryService.dto.InventoryDTO;
import com.ludogoriesoft.inventoryService.entities.Inventory;
import com.ludogoriesoft.inventoryService.exeptions.ApiRequestException;
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
    private final ProductServiceClient productServiceClient;
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
    public InventoryDTO getInventoryByProductId(Long id){
        Inventory inventory = inventoryRepository.getInventoryByProductId(id);
        if (inventory == null) {
            throw new ApiRequestException("Inventory not found");
        }
        return inventoryToInventoryDTO(inventory);
    }
    public InventoryDTO updateInventoryByProductId(Long id, InventoryDTO inventory) {
        Inventory foundInventory = inventoryRepository.getInventoryByProductId(id);
        if (foundInventory == null) {
            throw new ApiRequestException("Inventory not found");
        }
        foundInventory.setQuantity(inventory.getQuantity());
        foundInventory.setProduct(productServiceClient.getProductById(inventory.getProductId()));
        inventoryRepository.save(foundInventory);
        return inventory;
    }

}
