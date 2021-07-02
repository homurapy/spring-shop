package com.example.springshop.service;

import com.example.springshop.dto.ProductDto;
import com.example.springshop.model.Product;
import com.example.springshop.model.Review;
import com.example.springshop.repository.ProductRepository;
import com.example.springshop.service.spec.ProductSpec;
import com.example.springshop.util.MappingUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public List<ProductDto> findAll(Map<String, String> params) {
        final Specification<Product> specification = params.entrySet().stream()
                .filter(it-> StringUtils.hasText(it.getValue()))
                .map(it -> {
                    if ("name".equals(it.getKey())) {
                        return ProductSpec.nameLike(it.getValue());
                    }
                    if ("priceMax".equals(it.getKey())) {
                        return ProductSpec.priceLess(Integer.parseInt(it.getValue()));
                    }
                    if ("priceMin".equals(it.getKey())) {
                        return ProductSpec.priceGreater(Integer.parseInt(it.getValue()));
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(null);
        return repository.findAll(specification).stream()
                .map(MappingUtil::productDto)
                .collect(Collectors.toList());
    }

    public Product findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Review> getReview(Long id) {
        return repository.findById(id).orElseThrow().getReviews();
    }

    public Product saveProduct(Product product) {
        product.setCount(1);
        product.setReviews(null);
        return repository.save(product);
    }

    public Map<String, String> createFilter(String min, String max, String name) {
        Map<String, String> map = new HashMap<>();
        if(name != null) {
            map.put("name", name);
        }
        if(max != null) {
            map.put("priceMax", max);
        }
        if(max != null) {
            map.put("priceMin", min);
        }
        return map;
    }
}
