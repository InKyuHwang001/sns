package com.sns.api.model;


import com.sns.api.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
public class Post {

    private Integer id;

    private String title;

    private String body;

    private User user;

    private Timestamp registeredAt;

    private Timestamp updatedAt;

    private Timestamp removedAt;

    public static Post fromEntity(PostEntity entity) {
        return new Post(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                User.fromEntity(entity.getUser()),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getRemovedAt()
        );
    }

}