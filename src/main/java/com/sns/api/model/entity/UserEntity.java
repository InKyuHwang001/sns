package com.sns.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Table
@Entity
@Getter
public class UserEntity {

    @Id
    private Long id;
    @Column(name = "user_name")
    private String userName;
    private String password;

    @Builder
    public UserEntity(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
