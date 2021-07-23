package com.example.springshop.service;

import com.example.springshop.model.Review;
import com.example.springshop.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository repository;
    private final ProductService productService;


    public List<Review> findAllByProduct(Long id)
    {
        return repository.findReviewByProductId(id);
    }

    public Review addReviewToProduct(String review, Long id){
        var reviewAdd = new Review();
        reviewAdd.setText(review);
        reviewAdd.setProduct(productService.findById(id));
        return repository.saveAndFlush(reviewAdd);
    }

    public Review save(Review review) {
       return repository.save(review);
    }
    public void remove(Review review) {
        repository.delete(review);
    }
    public List<Review> findAll()
    {
        return repository.findAll();
    }

    public void setModerated(Long id){
        Review review = repository.findById(id).orElseThrow();
        review.setIsModerated(true);
        repository.save(review);
    }


}
