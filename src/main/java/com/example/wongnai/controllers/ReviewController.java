package com.example.wongnai.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wongnai.entitys.Review;
import com.example.wongnai.entitys.ReviewIndex;
import com.example.wongnai.entitys.ReviewIndexId;
import com.example.wongnai.exceptions.ResourceNotFoundException;
import com.example.wongnai.json.request.ReviewRequest;
import com.example.wongnai.model.FoodKeywordQueue;
import com.example.wongnai.repositories.ReviewIndexRepository;
import com.example.wongnai.repositories.ReviewRepository;
import com.example.wongnai.services.InvertedIndex;
import com.example.wongnai.services.ReviewService;
import com.example.wongnai.services.impl.FoodKeyWordIndexService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/reviews")
@Slf4j
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final ReviewIndexRepository reviewIndexRepository;
    private final ReviewService reviewService;

    public ReviewController(ReviewRepository reviewRepository, ReviewIndexRepository reviewIndexRepository, ReviewService service) {
        this.reviewRepository = reviewRepository;
        this.reviewIndexRepository = reviewIndexRepository;
        this.reviewService = service;
    }

    private InvertedIndex foodKeyWordIndexService = FoodKeyWordIndexService.getInstance();

    @GetMapping
    public List<Review> get() {
        List<Review> reviews = reviewRepository.findAll();
        log.info("reviews={}", reviews);
        return reviews;
    }

    @GetMapping("/{id}")
    public Review get(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping(params = "txt")
//    @GetMapping(value = "/{txt}")
    public List<Review> getByReview(@RequestParam String txt) {
        log.info("txt={}", txt);
        List<Review> reviews;
        List<Long> indexes = foodKeyWordIndexService.getIndexByKey(txt);
        if (indexes.size() == 0) {
            reviews = reviewRepository.findByReviewContaining(txt);
            FoodKeywordQueue queue = FoodKeywordQueue.builder().keyword(txt).reviews(reviews).build();
            FoodKeyWordIndexService.getInstance().addQueue(queue);
            return reviews;
        }
        reviews = reviewRepository.findAllById(indexes);
        return reviews;
    }

    @GetMapping(value = "/v1", params = "txt")
    public List<Review> getByReviewPrimitive(@RequestParam String txt){
        List<Review> reviews = reviewRepository.findByReviewContaining(txt);
        return reviews;
    }


    @PostMapping
    public Review create(@RequestBody Review req) {
        Review review = reviewRepository.save(req);
        return review;
    }

    @PutMapping("/{id}")
    public Review update(@PathVariable long id, @RequestBody ReviewRequest req) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isEmpty()) {
            throw new ResourceNotFoundException("Review.class");
//            throw new ResourceNotFoundException(Review.class);
        }
        Review review = reviewOptional.get();
        log.info("id={}, req={}", id, req);
        review.setReview(req.getMessage());
        return reviewRepository.save(review);
    }

    @GetMapping("/test")
    public Map<String, List<Long>> getIndex() {
        return FoodKeyWordIndexService.getInstance().getHashMap();
    }

    @GetMapping("/throw")
    public void throwThat(){
        throw new ResourceNotFoundException("test error");
    }

    @GetMapping("/queue")
    public Queue<FoodKeywordQueue> getQueues() {
        return FoodKeyWordIndexService.getInstance().getQueue();
    }

    @PostMapping("/post-review-index")
    public ReviewIndex postReviewIndex(@RequestBody ReviewIndexId reviewIndexId) {
        ReviewIndex reviewIndex = new ReviewIndex();
        reviewIndex.setReviewIndexId(reviewIndexId);
        return reviewIndexRepository.save(reviewIndex);
    }

    @GetMapping("/get-review-index-keyword/{keyword}")
    public List<ReviewIndex> getReviewIndexByKeyword(@PathVariable String keyword) {
        return reviewIndexRepository.findByReviewIndexIdKeyword(keyword);
    }

    @GetMapping("/get-review-index-review-id/{reviewId}")
    public List<ReviewIndex> getReviewIndexByReviewId(@PathVariable Long reviewId){
        return reviewIndexRepository.findByReviewIndexIdReviewId(reviewId);
    }
}
