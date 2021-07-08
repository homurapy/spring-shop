package com.example.springshop.service;

import com.example.springshop.dto.ProductDto;
import com.example.springshop.model.Product;
import com.example.springshop.model.Review;
import com.example.springshop.repository.ProductRepository;
import com.example.springshop.util.MappingUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public List <ProductDto> findAll(){
        return repository.findAll().stream().map(MappingUtil::productDto).collect(Collectors.toList());
    }
    public Product findById(Long id){
        return repository.findById(id).orElseThrow();
    }

    public List<Review> getReview(Long id){
        return repository.findById(id).orElseThrow().getReviews();
    }

    public Product saveProduct(Product product){
        product.setCount(1);
        product.setReviews(null);
       return repository.save(product);
    }
}
