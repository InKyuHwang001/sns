package com.sns.api.service;

import com.sns.api.controller.request.UserLoginRequest;
import com.sns.api.controller.request.UserSignupRequest;
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
        Optional<UserEntity> userEntity = userEntityRepository.findByUserName(userSignupRequest.getUserName());

        userEntityRepository.save(UserEntity.builder().build());
        return new User();
    }

    //TODO: develop
    public String login(UserLoginRequest userLoginRequest){
        UserEntity userEntity = userEntityRepository.findByUserName(userLoginRequest.getUserName())
                .orElseThrow(SnsApplicationException::new);

        if (!userEntity.getPassword().equals(userLoginRequest.getPassword())){
            throw new SnsApplicationException();
        }

        //TODO: 토큰 생성

        return "";
    }
}

