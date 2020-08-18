package com.example.wongnai.repositories;

import com.example.wongnai.entitys.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>{
    List<Review> findByReviewContaining(String review);
    Page<Review> findByReviewContaining(String txt, Pageable page);
}
