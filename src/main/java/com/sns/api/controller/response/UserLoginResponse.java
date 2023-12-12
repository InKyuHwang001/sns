package com.sns.api.controller.response;

import com.sns.api.model.User;
import com.sns.api.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {
    private String token;

}
