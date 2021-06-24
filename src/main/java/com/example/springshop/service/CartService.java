package com.example.springshop.service;

import com.example.springshop.dto.ProductDto;
import com.example.springshop.model.Cart;
import com.example.springshop.model.Product;
import com.example.springshop.model.Review;
import com.example.springshop.model.User;
import com.example.springshop.util.MappingUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService {
    private List<ProductDto> products;
    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
        this.products = new ArrayList<>();
    }

    public void addProduct(Set<ProductDto> items) {
        products.addAll(items);
    }

    public void deleteProduct(ProductDto product) {
        products.removeIf(p -> p.getId().equals(product.getId()));
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
        return products;
    }
    public Cart saveCart(){
        List <Product> productList = new ArrayList<>();
        for (int i = 0; i < products.size() ; i++) {
            ProductDto dto = products.get(i);
            productList.add(MappingUtil.dtoToProduct(dto, productService.getReview(dto.getId())));
        }
                return new Cart(new User(), productList);
    }
}

