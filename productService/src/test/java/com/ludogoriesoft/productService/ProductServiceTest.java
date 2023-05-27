package com.ludogoriesoft.productService;

import com.ludogoriesoft.productService.dto.ProductDTO;
import com.ludogoriesoft.productService.entities.Product;
import com.ludogoriesoft.productService.exeptions.ApiRequestException;
import com.ludogoriesoft.productService.repositories.ProductRepository;
import com.ludogoriesoft.productService.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testGetAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "test product 1", 10.0, "test manufacturer 1", "test description 1"));
        products.add(new Product(2L, "test product 2", 20.0, "test manufacturer 2", "test description 2"));
        List<ProductDTO> productDTOs = new ArrayList<>();
        productDTOs.add(new ProductDTO(1L, "test product 1", 10.0, "test manufacturer 1", "test description 1"));
        productDTOs.add(new ProductDTO(2L, "test product 2", 20.0, "test manufacturer 2", "test description 2"));
        when(productRepository.findAll()).thenReturn(products);
        when(modelMapper.map(products.get(0), ProductDTO.class)).thenReturn(productDTOs.get(0));
        when(modelMapper.map(products.get(1), ProductDTO.class)).thenReturn(productDTOs.get(1));

        List<ProductDTO> result = productService.getAllProducts();

        verify(productRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(products.get(0), ProductDTO.class);
        verify(modelMapper, times(1)).map(products.get(1), ProductDTO.class);
        assertEquals(productDTOs, result);
    }
    @Test
    public void testGetAllProductsWithNoProducts() {
        List<Product> products = new ArrayList<>();
        List<ProductDTO> productDTOs = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(products);

        List<ProductDTO> result = productService.getAllProducts();

        verify(productRepository, times(1)).findAll();
        assertEquals(productDTOs, result);
    }
    @Test
    public void testCreateProductWithNull() {
        Product product = null;
        ProductDTO productDTO = null;
        when(productRepository.save(product)).thenReturn(product);
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);
        ProductDTO result = productService.createProduct(product);
        assertNull(result);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        when(productRepository.save(product)).thenReturn(product);
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);
        ProductDTO result = productService.createProduct(product);
        verify(productRepository, times(1)).save(product);
        verify(modelMapper, times(1)).map(product, ProductDTO.class);
        assertEquals(productDTO, result);
    }
    @Test
    public void testUpdateExistingProductThenReturnsUpdatedProductDTO() {
        Long productId = 123L;
        Product existingProduct = new Product(productId, "Existing Product", 10.0, "Existing Manufacturer", "Existing Description");
        Product updatedProduct = new Product(productId, "Updated Product", 15.0, "Updated Manufacturer", "Updated Description");
        ProductDTO expectedProductDTO = new ProductDTO(productId, "Updated Product", 15.0, "Updated Manufacturer", "Updated Description");
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);
        when(modelMapper.map(existingProduct, ProductDTO.class)).thenReturn(expectedProductDTO);
        ProductDTO result = productService.updateProduct(productId, updatedProduct);
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
        verify(modelMapper, times(1)).map(existingProduct, ProductDTO.class);
        assertEquals(expectedProductDTO, result);
    }

    @Test
    public void testUpdateNonExistingProductThenThrowsApiRequestException() {
        Long productId = 123L;
        Product updatedProduct = new Product(productId, "Updated Product", 15.0, "Updated Manufacturer", "Updated Description");
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> productService.updateProduct(productId, updatedProduct));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any());
        verify(modelMapper, never()).map(any(), eq(ProductDTO.class));
    }
    @Test
    public void testGetProductByIdWithExistingProductIdThenReturnsProductDTO() {
        Long productId = 123L;
        Product existingProduct = new Product(productId, "Test Product", 10.0, "Test Manufacturer", "Test Description");
        ProductDTO expectedProductDTO = new ProductDTO(productId, "Test Product", 10.0, "Test Manufacturer", "Test Description");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(modelMapper.map(existingProduct, ProductDTO.class)).thenReturn(expectedProductDTO);

        ProductDTO result = productService.getProductById(productId);

        verify(productRepository, times(1)).findById(productId);
        verify(modelMapper, times(1)).map(existingProduct, ProductDTO.class);
        assertEquals(expectedProductDTO, result);
    }

    @Test
    public void testGetProductByIdWithNonExistingProductIdThenThrowsApiRequestException() {
        Long productId = 123L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> productService.getProductById(productId));
        verify(productRepository, times(1)).findById(productId);
        verify(modelMapper, never()).map(any(), eq(ProductDTO.class));
    }
    @Test
    public void testDeleteProductByIdWithExistingProductIdThenReturnsSuccess() {
        Long productId = 123L;
        int result = productService.deleteProductById(productId);
        verify(productRepository, times(1)).deleteById(productId);
        assertEquals(1, result);
    }

    @Test
    public void testDeleteProductByIdWithNonExistingProductIdThenReturnsFailure() {
        Long productId = 123L;
        doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(productId);
        int result = productService.deleteProductById(productId);
        verify(productRepository, times(1)).deleteById(productId);
        assertEquals(0, result);
    }

}
