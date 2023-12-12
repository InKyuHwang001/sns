package com.sns.api.fixture;


import com.sns.api.model.UserRole;
import com.sns.api.model.entity.PostEntity;
import com.sns.api.model.entity.UserEntity;

import java.sql.Timestamp;
import java.time.Instant;

public class PostEntityFixture {

    public static PostEntity get(String userName, Integer postId, Integer userId) {
        UserEntity user = new UserEntity();
        user.setUserName(userName);
        user.setId(userId);

        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId);
        return result;
    }
}