package com.sns.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sns.api.exception.ErrorCode;
import com.sns.api.exception.SnsApplicationException;
import com.sns.api.model.User;
import com.sns.api.controller.request.UserLoginRequest;
import com.sns.api.controller.request.UserSignupRequest;
import com.sns.api.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;


    @Test
    @DisplayName("[post /api/users/signup] 성공")
    public void signup1() throws Exception {
        var hwang = UserSignupRequest.builder()
                .userName("hwang")
                .password("1234")
                .build();

        when(userService.signup(hwang)).thenReturn(mock(User.class));

        mockMvc.perform(post("/api/users/join")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(hwang))
                ).andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    @DisplayName("[post /api/users/signup] 실패 >> 이미 존재하는 userName으로 하는 경우 에러반환")
    public void signup2() throws Exception{
        var hwang = UserSignupRequest.builder()
                .userName("hwang")
                .password("1234")
                .build();

        when(userService.signup(hwang)).thenThrow(new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("userName is %s", hwang.getUserName())));


        mockMvc.perform(post("/api/users/signup")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(hwang))
                ).andDo(print())
                .andExpect(status().isConflict());

    }

    @Test
    @DisplayName("[post /api/users/login] 성공")
    public void login1() throws Exception {
        var hwang = UserLoginRequest.builder()
                .userName("hwang")
                .password("1234")
                .build();

        when(userService.login(hwang)).thenReturn("test_token");

        mockMvc.perform(post("/api/users/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(hwang))
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("[post /api/users/login] 실패 >> 회원가입 안된 userName")
    public void login2() throws Exception {
        var hwang = UserLoginRequest.builder()
                .userName("hwang")
                .password("1234")
                .build();

        when(userService.login(hwang)).thenThrow(new SnsApplicationException());

        mockMvc.perform(post("/api/users/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(hwang))
                ).andDo(print())
                .andExpect(status().isNotFound());

    }



    @Test
    @DisplayName("[post /api/users/login] 실패 >> 틀린 password")
    public void login3() throws Exception {
        var hwang = UserLoginRequest.builder()
                .userName("hwang")
                .password("1234")
                .build();

        when(userService.login(hwang)).thenThrow(new SnsApplicationException());

        mockMvc.perform(post("/api/users/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(hwang))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}