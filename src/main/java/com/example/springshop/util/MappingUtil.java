package com.example.springshop.util;

import com.example.springshop.dto.ProductDto;
import com.example.springshop.model.Product;
import com.example.springshop.model.Review;
import com.example.springshop.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MappingUtil {

    public static ProductDto productDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice(),1D);
    }

    public static Product dtoToProduct(ProductDto dto, List<Review> reviewList) {
        return new Product(dto.getId(), dto.getName(), dto.getPrice(), dto.getCount(),reviewList);
    }


}
