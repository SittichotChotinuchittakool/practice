package com.example.wongnai.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.example.wongnai.entitys.Review;
import com.example.wongnai.model.FoodKeywordQueue;
import com.example.wongnai.model.InvertedIndexState;
import com.example.wongnai.services.InvertedIndex;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FoodKeyWordIndexService extends InvertedIndex<String, Long, FoodKeywordQueue> {
    private static FoodKeyWordIndexService foodKeyWordIndexService;
    private Map<String, List<Long>> hashMap = new HashMap<>();
    private InvertedIndexState state = InvertedIndexState.EMPTY;
    private Queue<FoodKeywordQueue> queue = new LinkedList<>();

    protected FoodKeyWordIndexService() {

    }

    public static InvertedIndex getInstance() {
        if (foodKeyWordIndexService == null) {
            log.info("create instance FoodKeyWordIndex");
            foodKeyWordIndexService = new FoodKeyWordIndexService();
            return foodKeyWordIndexService;
        }
        return foodKeyWordIndexService;
    }

    @Override
    public Map<String, List<Long>> getHashMap() {
        return hashMap;
    }

    @Override
    public void createHashMap() {
        FoodKeywordQueue foodKeywordQueue = queue.poll();
        state = InvertedIndexState.CREATE;
        log.info("foodKeywordQueue={}", foodKeywordQueue);
        if(foodKeywordQueue != null){
            String keyword = foodKeywordQueue.getKeyword();
            List<Long> ids = getListId(foodKeywordQueue.getReviews());
            hashMap.put(keyword, ids);
        }
//        log.info("queue.remove={}", queue.remove());
    }

    @Override
    public void start() {
        log.info("state={},hashset.size={},queue.size={}", state, hashMap.size(), queue.size());
        if (state.equals(InvertedIndexState.EMPTY)) {
            return;
        } else {
            if (state.equals(InvertedIndexState.AVAILABLE)) {
                createHashMap();
                updateQueueState();
            }
        }
    }

    @Override
    public Queue<FoodKeywordQueue> getQueue() {
        return queue;
    }

    public void addQueue(FoodKeywordQueue foodKeywordQueue) {
        queue.add(foodKeywordQueue);
        updateQueueState();
    }

    private void updateQueueState() {
        if (state.equals(InvertedIndexState.CREATE)) {
            if (queue.size() == 0) {
                state = InvertedIndexState.EMPTY;
                return;
            }
            state = InvertedIndexState.AVAILABLE;
        }
        if (state.equals(InvertedIndexState.EMPTY)) {
            state = InvertedIndexState.AVAILABLE;
        }
    }

    private List<Long> getListId(List<Review> reviews) {
        List<Long> ids = new ArrayList<>();
        for (Review review : reviews) {
            ids.add(review.getReviewID());
        }
        return ids;
    }
}
