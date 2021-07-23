package com.example.springshop.service;

import com.example.springshop.dto.ProductDto;
import com.example.springshop.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartServiceTest {
    @Autowired
    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService.clearCart();
        List<ProductDto> productDtos = List.of(new ProductDto(2L, "cow", 500, 1), new ProductDto(3L, "fish", 500, 1), new ProductDto(4L, "mouse", 1200, 1));
        cartService.getProducts().addAll(productDtos);
    }

    @AfterEach
    public void endTest() {
        cartService.clearCart();
    }

    @Test
    void addProductTest() {
        cartService.addProduct(new ProductDto(5L, "bear", 11500, 1));
        Assertions.assertEquals(4, cartService.getProducts().size());
    }

    @Test
    void deleteProductTest() {
        ProductDto productDto = new ProductDto(5L, "bear", 11500, 1);
        cartService.addProduct(productDto);
        cartService.deleteProduct(productDto);
        Assertions.assertEquals(3, cartService.getProducts().size());
    }

    @Test
    void updateQuantityTest() {
        ProductDto productDto = new ProductDto(5L, "bear", 11500, 1);
        cartService.addProduct(productDto);
        cartService.updateQuantity(productDto, 2);
        Assertions.assertEquals(2, cartService.getProducts().get(3).getCount());
    }

    @Test
    void clearCart() {
        Assertions.assertEquals(3, cartService.getProducts().size());
        cartService.clearCart();
        Assertions.assertEquals(0, cartService.getProducts().size());
    }
}