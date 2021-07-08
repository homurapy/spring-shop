package com.example.springshop.service;

import com.example.springshop.config.security.CustomUserDetailsService;
import com.example.springshop.dto.ProductDto;
import com.example.springshop.model.Order;
import com.example.springshop.model.User;
import com.example.springshop.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemRepository itemRepository;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Test
    void createOrderTest() {
        User userTest = new User();
        userTest.setId(1L);
        userTest.setName("asd");
        userTest.setLastName("asd");
        userTest.setLogin("asd");
        userTest.setPassword("asd");
        userTest.setPhone("213213213");
        userTest.setEmail("asd@sadas.3re");
        Mockito.when(userDetailsService.getUser())
                .thenReturn(userTest);
        List<ProductDto> productDtos = List.of(new ProductDto(2L, "cow", 500, 1), new ProductDto(3L, "fish", 500, 1), new ProductDto(4L, "mouse", 1200, 1));
        Order order = orderService.createOrder(productDtos);
        userDetailsService.getUser().setOrders(List.of(order));
        Assertions.assertEquals(2200, order.getTotalPrice());


    }

}