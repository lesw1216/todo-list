package com.sw.todolist.service.users;

import com.sw.todolist.domain.Users;

import java.util.Optional;

/*
* 저장
* 단건 조회
* 수정
* 삭제
* */
public interface UsersService {

    Users save(Users user);

    Optional<Users> findByUserId(String userId);

    void update(String userId, String password);

    void delete(String userId);
}
