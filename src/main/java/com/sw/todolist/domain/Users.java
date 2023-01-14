package com.sw.todolist.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Users {
    private Long id;
    private String userId;
    private String userName;
    private String password;

    public Users(Long id, String userId, String userName, String password) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
}
