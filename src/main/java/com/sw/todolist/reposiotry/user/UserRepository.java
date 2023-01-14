package com.sw.todolist.reposiotry.user;

import com.sw.todolist.domain.Users;

import java.util.Optional;

/*
* 저장
* 수정
* 삭제
* 조회
* */
public interface UserRepository {
    Users create(Users user);

    Optional<Users> read(String id);

    void update(String userId, String password);

    void delete(String userId);
}
