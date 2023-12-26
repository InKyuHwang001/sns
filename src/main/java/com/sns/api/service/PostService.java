package com.sns.api.service;

import com.sns.api.exception.SnsApplicationException;
import com.sns.api.model.Comment;
import com.sns.api.model.Post;
import com.sns.api.model.entity.CommentEntity;
import com.sns.api.model.entity.LikeEntity;
import com.sns.api.model.entity.PostEntity;
import com.sns.api.model.entity.UserEntity;
import com.sns.api.repository.CommentEntityRepository;
import com.sns.api.repository.LikeEntityRepository;
import com.sns.api.repository.PostEntityRepository;
import com.sns.api.repository.UserEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import static com.sns.api.exception.ErrorCode.*;
import static com.sns.api.exception.ErrorCode.ALREADY_LIKED_POST;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final LikeEntityRepository likeEntityRepository;
    private final CommentEntityRepository commentEntityRepository;


    @Transactional
    public void create(String title, String body, String userName) {
        UserEntity userEntity = getUserEntityOrExceptions(userName);

        postEntityRepository.save(PostEntity.of(title, body, userEntity));

    }

    @Transactional
    public Post modify(String title, String body, String userName, Integer postId) {
        UserEntity userEntity = getUserEntityOrExceptions(userName);
        PostEntity postEntity = getPostOrException(postId);

        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(INVALID_PERMISSION, String.format("user %s has no permission with post %d", userName, postId));
        }

        postEntity.setTitle(title);
        postEntity.setBody(body);

        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));
    }

    @Transactional
    public void delete(String userName, Integer postId) {
        UserEntity userEntity = getUserEntityOrExceptions(userName);
        PostEntity postEntity = getPostOrException(postId);

        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(INVALID_PERMISSION, String.format("user %s has no permission with post %d", userName, postId));
        }

        postEntityRepository.delete(postEntity);
    }

    public Page<Post> list(Pageable pageable) {
        return postEntityRepository.findAll(pageable).map(Post::fromEntity);
    }

    public Page<Post> my(String userName, Pageable pageable) {
        UserEntity userEntity = getUserEntityOrExceptions(userName);

        return postEntityRepository.findAllByUser(userEntity, pageable).map(Post::fromEntity);
    }

    @Transactional
    public void like(Integer postId, String userName) {
        UserEntity userEntity = getUserEntityOrExceptions(userName);
        PostEntity postEntity = getPostOrException(postId);

        likeEntityRepository.findByUserAndPost(userEntity, postEntity).ifPresent(it -> {
            throw new SnsApplicationException(ALREADY_LIKED_POST, String.format("userName %s already like the post %s", userName, postId));
        });

        likeEntityRepository.save(LikeEntity.of(postEntity, userEntity));
    }

    public int likeCount(Integer postId) {
        PostEntity postEntity = getPostOrException(postId);

        return likeEntityRepository.countByPost(postEntity);
    }

    @Transactional
    public void comment(Integer postId, String userName, String comment) {
        UserEntity userEntity = getUserEntityOrExceptions(userName);
        PostEntity postEntity = getPostOrException(postId);

        commentEntityRepository.save(CommentEntity.of(postEntity, userEntity, comment));
    }

    public Page<Comment> getComment(Integer postId, Pageable pageable) {
        PostEntity postEntity = getPostOrException(postId);

        return commentEntityRepository.findAllByPost(postEntity, pageable).map(Comment::fromEntity);
    }

    /**
     * Post exist
     *
     * @param postId
     * @return
     */
    private PostEntity getPostOrException(Integer postId) {
        return postEntityRepository.findById(postId).orElseThrow(() ->
                new SnsApplicationException(POST_NOT_FOUND, String.format("%s not founded", postId)));
    }

    /**
     * User exist
     *
     * @param userName
     * @return
     */

    private UserEntity getUserEntityOrExceptions(String userName) {
        return userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(USER_NOT_FOUND, String.format("%s not founded", userName)));
    }

}

