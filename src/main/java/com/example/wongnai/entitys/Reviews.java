package com.example.wongnai.entitys;

import java.util.ArrayList;
import java.util.List;

public class Reviews extends ArrayList<Review> {

    public List<Long> getListId(Reviews reviews) {
        List<Long> ids = new ArrayList<>();
        for(Review review : reviews){
            ids.add(review.getReviewID());
        }
        return ids;
    }
}
