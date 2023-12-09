package com.sns.api.service;

import com.sns.api.controller.request.UserLoginRequest;
import com.sns.api.controller.request.UserSignupRequest;
import com.sns.api.exception.SnsApplicationException;
import com.sns.api.model.entity.UserEntity;
import com.sns.api.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    @DisplayName("signup 정상 작동")
    void signup1(){

        UserSignupRequest request = UserSignupRequest.builder()
                .userName("userName")
                .password("1234")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .userName(request.getUserName())
                .password(request.getPassword())
                .build();

        when(userEntityRepository.findByUserName(request.getUserName())).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(userEntity));

        assertDoesNotThrow(()-> userService.signup(request));
    }

    @Test
    @DisplayName("signup 싪패 >>userName 중복")
    void signup2(){

        UserSignupRequest request = UserSignupRequest.builder()
                .userName("userName")
                .password("1234")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .userName(request.getUserName())
                .password(request.getPassword())
                .build();

        when(userEntityRepository.findByUserName(request.getUserName())).thenReturn(Optional.of(userEntity));
        when(userEntityRepository.save(any())).thenReturn(Optional.of(userEntity));

        assertThrows(SnsApplicationException.class, ()-> userService.signup(request));
    }


    @Test
    @DisplayName("login 정상 작동")
    void login(){

        UserLoginRequest request = UserLoginRequest.builder()
                .userName("userName")
                .password("1234")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .userName(request.getUserName())
                .password(request.getPassword())
                .build();

        when(userEntityRepository.findByUserName(request.getUserName())).thenReturn(Optional.of(userEntity));

        assertDoesNotThrow(()-> userService.login(request));
    }

    @Test
    @DisplayName("login 싪패 >>userName 없는 경우")
    void login2(){

        UserLoginRequest request = UserLoginRequest.builder()
                .userName("userName")
                .password("1234")
                .build();

        when(userEntityRepository.findByUserName(request.getUserName())).thenReturn(Optional.empty());

        assertThrows(SnsApplicationException.class, ()-> userService.login(request));
    }


    @Test
    @DisplayName("login 싪패 >>password 가 틀린 경우")
    void login3(){

        UserLoginRequest request = UserLoginRequest.builder()
                .userName("userName")
                .password("1234")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .userName(request.getUserName())
                .password(request.getPassword() + "1")
                .build();

        when(userEntityRepository.findByUserName(request.getUserName())).thenReturn(Optional.of(userEntity));

        assertThrows(SnsApplicationException.class, ()-> userService.login(request));
    }
}