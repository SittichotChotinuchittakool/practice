package com.example.wongnai.model;

import com.example.wongnai.entitys.Review;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FoodKeywordQueue {
    private String keyword;
    private List<Review> reviews;
}
