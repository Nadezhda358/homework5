package com.ludogoriesoft.orderService;

import com.ludogoriesoft.orderService.dto.OrderDTO;
import com.ludogoriesoft.orderService.entities.Order;
import com.ludogoriesoft.orderService.repositories.OrderRepository;
import com.ludogoriesoft.orderService.services.OrderProductsService;
import com.ludogoriesoft.orderService.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private OrderProductsService orderProductsService;

    @Test
    public void testGetAllOrdersWhenOrdersExisThenReturnsOrderDTOs() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, LocalDateTime.now(), "Customer 1"));
        orders.add(new Order(2L, LocalDateTime.now(), "Customer 2"));
        List<OrderDTO> orderDTOs = new ArrayList<>();
        orderDTOs.add(new OrderDTO(1L, LocalDateTime.now(), "Customer 1"));
        orderDTOs.add(new OrderDTO(2L, LocalDateTime.now(), "Customer 2"));

        when(orderRepository.findAll()).thenReturn(orders);
        when(modelMapper.map(orders.get(0), OrderDTO.class)).thenReturn(orderDTOs.get(0));
        when(modelMapper.map(orders.get(1), OrderDTO.class)).thenReturn(orderDTOs.get(1));
        List<OrderDTO> result = orderService.getAllOrders();

        verify(orderRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(orders.get(0), OrderDTO.class);
        verify(modelMapper, times(1)).map(orders.get(1), OrderDTO.class);
        assertEquals(orderDTOs, result);
    }

    @Test
    public void testGetAllOrdersWithNoOrdersThenReturnsEmptyList() {
        List<Order> orders = new ArrayList<>();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(orders);
        List<OrderDTO> result = orderService.getAllOrders();
        verify(orderRepository, times(1)).findAll();
        assertEquals(orderDTOs, result);
    }


}
