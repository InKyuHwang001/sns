package com.sns.api.fixture;


import com.sns.api.model.UserRole;
import com.sns.api.model.entity.UserEntity;

import java.sql.Timestamp;
import java.time.Instant;

public class UserEntityFixture {

    public static UserEntity get(String userName, String password, Integer userId) {
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        entity.setUserName(userName);
        entity.setPassword(password);
        entity.setRole(UserRole.USER);
        entity.setRegisteredAt(Timestamp.from(Instant.now()));
        return entity;
    }
}