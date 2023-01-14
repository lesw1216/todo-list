package com.sw.todolist.reposiotry.user;

import com.sw.todolist.domain.Users;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class UserRepositoryTest {
    @Autowired
    UserRepository repository;

    @Test
    void create() {
        Users users = new Users(1L, "userA", "유저A", "dbwja");
        Users createUser = repository.create(users);
        Assertions.assertThat(createUser).isEqualTo(users);
    }

    @Test
    void read() {
        new Users(1L, "userA", "유저A", "1234");

    }
}