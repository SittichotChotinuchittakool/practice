package com.example.wongnai.entitys;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.aspectj.weaver.ast.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "review")
@Data
public class Review {
    @Id
    @Setter(AccessLevel.NONE)
    private long reviewID;

    @Column(columnDefinition = "TEXT")
    private String review;
}
