package com.ludogoriesoft.productService;

import com.ludogoriesoft.productService.controllers.ProductController;
import com.ludogoriesoft.productService.dto.ProductDTO;
import com.ludogoriesoft.productService.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Test
    public void testGetAllProducts() throws Exception {
        ProductDTO product1 = new ProductDTO(1L, "TestName", 200.0, "manufacturer1", "description1");
        ProductDTO product2 = new ProductDTO(2L, "TestName2", 300.0, "manufacturer2", "description2");
        List<ProductDTO> productList = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("TestName")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price", is(200.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manufacturer", is("manufacturer1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", is("description1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", is("TestName2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price", is(300.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].manufacturer", is("manufacturer2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description", is("description2")));
        verify(productService, times(1)).getAllProducts();
        verifyNoMoreInteractions(productService);
    }
}
