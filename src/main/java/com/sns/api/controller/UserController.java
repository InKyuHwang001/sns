package com.sns.api.controller;


import com.sns.api.controller.request.UserLoginRequest;
import com.sns.api.controller.request.UserSignupRequest;
import com.sns.api.controller.response.Response;
import com.sns.api.controller.response.UserSignUpResponse;
import com.sns.api.model.User;
import com.sns.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public Response<UserSignUpResponse> signup(@RequestBody UserSignupRequest userSignupRequest) {
        User signup = userService.signup(userSignupRequest);
        return Response.success(UserSignUpResponse.fromUser(signup));
    }

    @PostMapping
    public void login(UserLoginRequest userLoginRequest) {
        userService.login(userLoginRequest);
    }


}
