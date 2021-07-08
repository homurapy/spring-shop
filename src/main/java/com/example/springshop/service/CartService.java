package com.example.springshop.service;

import com.example.springshop.dto.ProductDto;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@SessionScope
public class CartService {
    private List<ProductDto> products;


    public CartService() {
        this.products = new ArrayList<>();
    }

    public void addProduct(ProductDto product) {
        products.add(product);
    }

    public void addProduct(Set<ProductDto> products) {
        this.products.addAll(products.stream().map(
                product -> {
                    var newProduct = new ProductDto();
                    newProduct.setCount(1);
                    newProduct.setPrice(product.getPrice());
                    newProduct.setName(product.getName());
                    newProduct.setId(product.getId());
                    return newProduct;
                }
        ).collect(Collectors.toList()));
    }

    public void deleteProduct(ProductDto product) {
        products.removeIf(p -> p.getId().equals(product.getId()));
    }

    public ProductDto updateQuantity(ProductDto dto, Integer entity) {
        if (entity == 0) {
            deleteProduct(dto);
            return null;
        } else {
            if (dto.getCount() < entity) {
                increaseProductCount(dto);
            } else {
                decreaseProductCount(dto);
            }
        }
        return dto;
    }

    public void increaseProductCount(ProductDto dto) {
        dto.setCount(dto.getCount() + 1);
        return;
    }

    public void decreaseProductCount(ProductDto dto) {
        dto.setCount(dto.getCount() - 1);
        if (dto.getCount() == 0) {
            products.remove(dto);
        }
    }

    public List<ProductDto> getProducts() {
        if (products == null) {
            return null;
        }
        return products;
    }

    public List<ProductDto> clearCart() {
        products.clear();
        return products;
    }}

