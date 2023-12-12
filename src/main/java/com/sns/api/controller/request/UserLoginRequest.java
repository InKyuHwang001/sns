package com.sns.api.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginRequest {

    private String userName;

    private String password;

    @Builder
    public UserLoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
