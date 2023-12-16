package com.sns.api.service;

import com.sns.api.exception.ErrorCode;
import com.sns.api.exception.SnsApplicationException;
import com.sns.api.fixture.PostEntityFixture;
import com.sns.api.fixture.UserEntityFixture;
import com.sns.api.model.entity.PostEntity;
import com.sns.api.model.entity.UserEntity;
import com.sns.api.repository.PostEntityRepository;
import com.sns.api.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @MockBean
    UserEntityRepository userEntityRepository;

    @MockBean
    PostEntityRepository postEntityRepository;


    @Test
    void 포스트_생성시_정상동작한다() {

        String tittle = "tittle";
        String body = "body";
        String userName = "userName";

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        assertDoesNotThrow(() -> postService.create(tittle, body, userName));
    }


    @Test
    void 포스트생성시_유저가_존재하지_않으면_에러를_내뱉는다() {
        String tittle = "tittle";
        String body = "body";
        String userName = "userName";

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        var exception = assertThrows(SnsApplicationException.class, () -> postService.create(tittle, body, userName));
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 포스트_수정시_정상동작한다() {

        String tittle = "tittle";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));
        when(postEntityRepository.saveAndFlush(any())).thenReturn(postEntity);

        assertDoesNotThrow(() -> postService.modify(tittle, body, userName, postId));
    }

    @Test
    void 포스트수정시_포스트가_존재하지_않으면_에러를_내뱉는다() {
        String tittle = "tittle";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());

        var exception = assertThrows(SnsApplicationException.class, () -> postService.modify(tittle, body, userName, postId));
        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 포스트수정시_유저의_권한이_존재하지_않으면_에러를_내뱉는다() {
        String tittle = "tittle";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity writer = UserEntityFixture.get("userName1", "aaaaa", 2);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));


        var exception = assertThrows(SnsApplicationException.class, () -> postService.modify(tittle, body, userName, postId));
        assertEquals(ErrorCode.INVALID_PERMISSION, exception.getErrorCode());
    }

    @Test
    void 포스트_삭제시_정상동작한다() {


        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        assertDoesNotThrow(() -> postService.delete(userName, postId));
    }

    @Test
    void 포스트삭제시_포스트가_존재하지_않으면_에러를_내뱉는다() {
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());

        var exception = assertThrows(SnsApplicationException.class, () -> postService.delete(userName, postId));
        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 포스트삭제시_유저의_권한이_존재하지_않으면_에러를_내뱉는다() {
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity writer = UserEntityFixture.get("userName1", "aaaaa", 2);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));


        var exception = assertThrows(SnsApplicationException.class, () -> postService.delete(userName, postId));
        assertEquals(ErrorCode.INVALID_PERMISSION, exception.getErrorCode());
    }

    @Test
    void 피드목록_요청이_정상동작한다() {

        Pageable pageable = mock(Pageable.class);
        when(postEntityRepository.findAll(pageable)).thenReturn(Page.empty());

        assertDoesNotThrow(() -> postService.list(pageable));
    }

    @Test
    void 내피드목록_요청이_정상동작한다() {
        Pageable pageable = mock(Pageable.class);
        UserEntity user = mock(UserEntity.class);

        when(userEntityRepository.findByUserName(any())).thenReturn(Optional.of(user));
        when(postEntityRepository.findAllByUser(user, pageable)).thenReturn(Page.empty());

        assertDoesNotThrow(() -> postService.my("", pageable));
    }

}