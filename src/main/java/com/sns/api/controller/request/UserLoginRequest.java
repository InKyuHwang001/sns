package com.sns.api.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginRequest {

    private String name;

    private String password;

    @Builder
    public UserLoginRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
