package com.example.wongnai.services;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import com.example.wongnai.entitys.Reviews;
import com.example.wongnai.model.FoodKeywords;
import com.example.wongnai.repositories.ReviewRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ImportData implements InitializingBean {
    private final RestOperations restOperations;

    private final ReviewRepository reviewRepository;

    @Value("${we.challenge.review}")
    private String urlReview;

    @Value("${we.challenge.food-keyword}")
    private String urlFoodKeyword;

    public ImportData(RestOperations restOperations, ReviewRepository reviewRepository) {
        this.restOperations = restOperations;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ResponseEntity<Reviews> responseEntity = restOperations.getForEntity(urlReview, Reviews.class);
        Reviews reviews = responseEntity.getBody();
        reviewRepository.deleteAll();
        log.info("clear data in review.");
        reviewRepository.saveAll(reviews);
        log.info("save data in review.");
        log.info("review={}", reviews.size());
        ResponseEntity<FoodKeywords> responseFoodKeywords = restOperations.getForEntity(urlFoodKeyword, FoodKeywords.class);
        FoodKeywords foodKeywords = responseFoodKeywords.getBody();
        log.info("foodKeyword={}", foodKeywords.size());
    }
}
