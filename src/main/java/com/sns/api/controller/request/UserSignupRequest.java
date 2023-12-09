package com.sns.api.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSignupRequest {

    private String userName;

    private String password;

    @Builder
    public UserSignupRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
