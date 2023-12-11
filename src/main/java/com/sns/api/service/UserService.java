package com.sns.api.service;

import com.sns.api.controller.request.UserLoginRequest;
import com.sns.api.controller.request.UserSignupRequest;
import com.sns.api.exception.ErrorCode;
import com.sns.api.exception.SnsApplicationException;
import com.sns.api.model.User;
import com.sns.api.model.entity.UserEntity;
import com.sns.api.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    //TODO: develop
    public User signup(UserSignupRequest userSignupRequest){
        userEntityRepository.findByUserName(userSignupRequest.getUserName()).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("userName is %s", userSignupRequest.getUserName()));
        });

        UserEntity save = userEntityRepository.save(UserEntity.of(userSignupRequest.getUserName(), userSignupRequest.getPassword()));
        return new User().fromEntity(save);
    }

    //TODO: develop
    public String login(UserLoginRequest userLoginRequest){
//        UserEntity userEntity = userEntityRepository.findByUserName(userLoginRequest.getUserName()).orElseThrow(new SnsApplicationException(ErrorCode.DATABASE_ERROR," "));
//
//        if (!userEntity.getPassword().equals(userLoginRequest.getPassword())){
//            throw new SnsApplicationException();
//        }

        //TODO: 토큰 생성

        return "";
    }
}

