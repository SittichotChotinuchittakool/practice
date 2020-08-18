package com.example.wongnai.repositories;

import com.example.wongnai.entitys.ReviewIndex;
import com.example.wongnai.entitys.ReviewIndexId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewIndexRepository extends JpaRepository<ReviewIndex, ReviewIndexId> {
    List<ReviewIndex> findByReviewIndexIdKeyword(String keyword);
    List<ReviewIndex> findByReviewIndexIdReviewId(long id);
}
