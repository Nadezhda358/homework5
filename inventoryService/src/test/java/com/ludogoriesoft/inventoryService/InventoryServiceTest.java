package com.ludogoriesoft.inventoryService;

import com.ludogoriesoft.inventoryService.config.ProductServiceClient;
import com.ludogoriesoft.inventoryService.dto.InventoryDTO;
import com.ludogoriesoft.inventoryService.entities.Inventory;
import com.ludogoriesoft.inventoryService.entities.Product;
import com.ludogoriesoft.inventoryService.exeptions.ApiRequestException;
import com.ludogoriesoft.inventoryService.repositories.InventoryRepository;
import com.ludogoriesoft.inventoryService.services.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class InventoryServiceTest {
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @InjectMocks
    private InventoryService inventoryService;
    @Mock
    private InventoryRepository inventoryRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ProductServiceClient productServiceClient;

    @Test
    public void testGetAllInventories() {
        List<Inventory> inventories = new ArrayList<>();
        inventories.add(new Inventory(1L, 10, new Product(1L, "test product 1", 10.0, "test manufacturer 1", "test description 1")));
        inventories.add(new Inventory(2L, 5, new Product(2L, "test product 2", 20.0, "test manufacturer 2", "test description 2")));

        List<InventoryDTO> expectedInventoryDTOs = new ArrayList<>();
        expectedInventoryDTOs.add(new InventoryDTO(1L, 10, 1L));
        expectedInventoryDTOs.add(new InventoryDTO(2L, 5, 2L));

        when(inventoryRepository.findAll()).thenReturn(inventories);
        when(modelMapper.map(inventories.get(0), InventoryDTO.class)).thenReturn(expectedInventoryDTOs.get(0));
        when(modelMapper.map(inventories.get(1), InventoryDTO.class)).thenReturn(expectedInventoryDTOs.get(1));

        List<InventoryDTO> result = inventoryService.getAllInventories();

        verify(inventoryRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(inventories.get(0), InventoryDTO.class);
        verify(modelMapper, times(1)).map(inventories.get(1), InventoryDTO.class);
        assertEquals(expectedInventoryDTOs, result);
    }
    @Test
    public void testGetAllInventoriesWithNoInventories() {
        List<Inventory> inventories = new ArrayList<>();
        List<InventoryDTO> expectedInventoryDTOs = new ArrayList<>();
        when(inventoryRepository.findAll()).thenReturn(inventories);
        List<InventoryDTO> result = inventoryService.getAllInventories();
        verify(inventoryRepository, times(1)).findAll();
        assertEquals(expectedInventoryDTOs, result);
    }
    @Test
    public void testGetInventoryByProductIdWithExistingProductIdThenReturnsInventoryDTO() {
        Long productId = 123L;
        Inventory inventory = new Inventory(1L, 10, new Product(123L, "testName", 1.50, "testManufacturer", "testDescription"));
        InventoryDTO expectedInventoryDTO = new InventoryDTO(1L, 10, 123L);
        when(inventoryRepository.getInventoryByProductId(productId)).thenReturn(inventory);
        when(modelMapper.map(inventory, InventoryDTO.class)).thenReturn(expectedInventoryDTO);
        InventoryDTO result = inventoryService.getInventoryByProductId(productId);
        verify(inventoryRepository, times(1)).getInventoryByProductId(productId);
        assertEquals(expectedInventoryDTO, result);
    }

    @Test
    public void testGetInventoryByProductIdWithNonExistingProductIdThenThrowsApiRequestException() {
        Long productId = 456L;
        when(inventoryRepository.getInventoryByProductId(productId)).thenReturn(null);
        assertThrows(ApiRequestException.class, () -> inventoryService.getInventoryByProductId(productId));
        verify(inventoryRepository, times(1)).getInventoryByProductId(productId);
    }

    @Test
    public void testUpdateInventoryByProductIdWithExistingInventoryThenReturnsUpdatedInventoryDTO() {
        Long inventoryId = 1L;
        Long productId = 123L;
        Inventory foundInventory = new Inventory(inventoryId, 10, new Product(123L, "testName", 1.50, "testManufacturer", "testDescription"));
        InventoryDTO updatedInventoryDTO = new InventoryDTO(inventoryId, 20, productId);
        when(inventoryRepository.getInventoryByProductId(inventoryId)).thenReturn(foundInventory);
        Product product = new Product(productId, "testName", 1.50, "testManufacturer", "testDescription");
        when(productServiceClient.getProductById(productId)).thenReturn(product);
        when(inventoryRepository.save(foundInventory)).thenReturn(foundInventory);
        InventoryDTO result = inventoryService.updateInventoryByProductId(inventoryId, updatedInventoryDTO);
        verify(inventoryRepository, times(1)).getInventoryByProductId(inventoryId);
        verify(productServiceClient, times(1)).getProductById(productId);
        verify(inventoryRepository, times(1)).save(foundInventory);
        assertEquals(updatedInventoryDTO, result);
    }

    @Test
    public void testUpdateInventoryByProductIdWithNonExistingInventoryThenThrowsApiRequestException() {
        Long inventoryId = 1L;
        InventoryDTO updatedInventoryDTO = new InventoryDTO(inventoryId, 20, 123L);
        when(inventoryRepository.getInventoryByProductId(inventoryId)).thenReturn(null);
        assertThrows(ApiRequestException.class, () -> inventoryService.updateInventoryByProductId(inventoryId, updatedInventoryDTO));
        verify(inventoryRepository, times(1)).getInventoryByProductId(inventoryId);
    }

    @Test
    public void testUpdateInventoryByProductIdWhenProductNotFoundThenThrowsApiRequestException() {
        Long inventoryId = 1L;
        Long productId = 123L;
        Inventory foundInventory = new Inventory(inventoryId, 10, new Product(123L, "testName", 1.50, "testManufacturer", "testDescription"));
        InventoryDTO updatedInventoryDTO = new InventoryDTO(inventoryId, 20, productId);
        when(inventoryRepository.getInventoryByProductId(inventoryId)).thenReturn(foundInventory);
        when(productServiceClient.getProductById(productId)).thenThrow(new ApiRequestException("Product not found"));
        assertThrows(ApiRequestException.class, () -> inventoryService.updateInventoryByProductId(inventoryId, updatedInventoryDTO));
        verify(inventoryRepository, times(1)).getInventoryByProductId(inventoryId);
        verify(productServiceClient, times(1)).getProductById(productId);
    }
@Test
public void testCreateInventoryWhenNewInventoryThenReturnsInventoryDTO() {
    Long productId = 123L;
    InventoryDTO inventoryDTO = new InventoryDTO(null, 10, productId);
    Product product = new Product(productId, "testName", 1.50, "testManufacturer", "testDescription");
    when(inventoryRepository.getInventoryByProductId(productId)).thenReturn(null);
    when(productServiceClient.getProductById(productId)).thenReturn(product);
    when(inventoryRepository.save(any(Inventory.class))).thenAnswer(invocation -> invocation.getArgument(0));
    InventoryDTO result = inventoryService.createInventory(inventoryDTO);
    verify(inventoryRepository, times(1)).getInventoryByProductId(productId);
    verify(productServiceClient, times(1)).getProductById(productId);
    verify(inventoryRepository, times(1)).save(any(Inventory.class));
    assertEquals(inventoryDTO, result);
}

    @Test
    public void testCreateInventoryWithExistingInventoryThenThrowsApiRequestException() {
        Long productId = 123L;
        InventoryDTO inventoryDTO = new InventoryDTO(null, 10, productId);
        when(inventoryRepository.getInventoryByProductId(productId)).thenReturn(new Inventory());
        assertThrows(ApiRequestException.class, () -> inventoryService.createInventory(inventoryDTO));
        verify(inventoryRepository, times(1)).getInventoryByProductId(productId);
    }

    @Test
    public void testCreateInventoryWhenProductNotFoundThenThrowsApiRequestException() {
        Long productId = 123L;
        InventoryDTO inventoryDTO = new InventoryDTO(null, 10, productId);
        when(inventoryRepository.getInventoryByProductId(productId)).thenReturn(null);
        when(productServiceClient.getProductById(productId)).thenThrow(new ApiRequestException("Product not found"));
        assertThrows(ApiRequestException.class, () -> inventoryService.createInventory(inventoryDTO));
        verify(inventoryRepository, times(1)).getInventoryByProductId(productId);
        verify(productServiceClient, times(1)).getProductById(productId);
    }


}
