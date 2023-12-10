package com.sns.api.controller.response;

import com.sns.api.model.User;
import com.sns.api.model.UserRole;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserSignUpResponse {
    private Integer id;
    private String name;
    private UserRole role;

    public static UserSignUpResponse fromUser(User user) {
        return new UserSignUpResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}
