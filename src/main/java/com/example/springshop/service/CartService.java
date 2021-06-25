package com.example.springshop.service;

import com.example.springshop.dto.ProductDto;
import com.example.springshop.model.Cart;
import com.example.springshop.model.Product;
import com.example.springshop.model.User;
import com.example.springshop.repository.CartRepository;
import com.example.springshop.util.MappingUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService {
    private List<ProductDto> products;
    private final ProductService productService;
    private final CartRepository cartRepository;

    public CartService(ProductService productService, CartRepository cartRepository) {
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.products = new ArrayList<>();
    }

    public void addProduct(ProductDto product) {
        products.add(product);
    }

    public void addProduct(Set<ProductDto> products) {
        this.products.addAll(products.stream().map(
                product -> {
                    var newProduct = new ProductDto();
                    newProduct.setCount(1D);
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

    public ProductDto updateQuantity(ProductDto dto, Double entity){
        if (entity == 0) {
            deleteProduct(dto);
        return null;
        }
        else {
            if (dto.getCount() < entity) {
                increaseProductCount(dto);
            }
            else {
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
        if (products == null){
            return null;
        }
        return products;
    }
    public Cart saveCart(List <ProductDto> products){
        List <Product> productList = new ArrayList<>();
        for (int i = 0; i < products.size() ; i++) {
            ProductDto dto = products.get(i);
            productList.add(MappingUtil.dtoToProduct(dto, productService.getReview(dto.getId())));
        }
        Cart cart = new Cart(new User(), productList);
        cartRepository.saveAndFlush(cart);
                return cart;
    }
    public List<ProductDto> clearCart(){
          products.clear();
          return products;
    }
}

