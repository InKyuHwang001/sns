package com.sns.api.service;

import com.sns.api.exception.ErrorCode;
import com.sns.api.exception.SnsApplicationException;
import com.sns.api.model.entity.PostEntity;
import com.sns.api.model.entity.UserEntity;
import com.sns.api.repository.PostEntityRepository;
import com.sns.api.repository.UserEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String userName) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(
                        ErrorCode.USER_NOT_FOUND,
                        String.format("%s not founded", userName)));

        postEntityRepository.save(new PostEntity());

    }
}
