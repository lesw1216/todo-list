package com.sw.todolist.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String userId;
    private String username;
    private String password;

    public UserDto() {
    }

    public UserDto(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }


}
