package com.example.wongnai.entitys;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "review_index")
public class ReviewIndex {
    public ReviewIndex() {

    }
    @EmbeddedId private ReviewIndexId reviewIndexId;
}
