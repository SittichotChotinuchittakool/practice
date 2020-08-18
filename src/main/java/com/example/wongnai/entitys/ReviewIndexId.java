package com.example.wongnai.entitys;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Data;

@Data
public class ReviewIndexId implements Serializable {
    private String keyword;
    @Column(name = "review_id")
    private long reviewId;
}
