package com.example.wongnai.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wongnai.entitys.Review;
import com.example.wongnai.exceptions.ResourceNotFoundException;
import com.example.wongnai.repositories.ReviewRepository;

@Service
public class ReviewService{
    @Autowired
    private ReviewRepository reviewRepository;

    public Review getReviewById(Long id){
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if(reviewOptional.isEmpty()) {
            throw new ResourceNotFoundException(Review.class);
//            throw new ResourceNotFoundException();
//            throw new RuntimeException("Review Not Found");
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found");
        }
        return reviewOptional.get();
    }
}
