package com.sns.api.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserJoinRequest {

    private String name;

    private String password;

    @Builder
    public UserJoinRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
