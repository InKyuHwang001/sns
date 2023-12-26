package com.sns.api.controller;


import com.sns.api.controller.request.UserJoinRequest;
import com.sns.api.controller.request.UserLoginRequest;
import com.sns.api.controller.response.AlarmResponse;
import com.sns.api.controller.response.Response;
import com.sns.api.controller.response.UserLoginResponse;
import com.sns.api.controller.response.UserJoinResponse;
import com.sns.api.exception.ErrorCode;
import com.sns.api.exception.SnsApplicationException;
import com.sns.api.model.User;
import com.sns.api.service.UserService;
import com.sns.api.util.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        return Response.success(UserJoinResponse.fromUser(userService.join(request.getName(), request.getPassword())));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getName(), request.getPassword());

        return Response.success(new UserLoginResponse(token));
    }

    @GetMapping("/alarm")
    public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication) {
        User user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), User.class).orElseThrow(
                ()->new SnsApplicationException(ErrorCode.INTERNAL_SERVER_ERROR,
                "Casting to User class failed"));

        return Response.success(userService.alarmList(user.getId(), pageable).map(AlarmResponse::fromAlarm));
    }

}
