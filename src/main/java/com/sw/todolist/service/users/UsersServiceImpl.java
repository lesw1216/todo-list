package com.sw.todolist.service.users;

import com.sw.todolist.domain.Users;
import com.sw.todolist.reposiotry.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService{

    private final UserRepository repository;

    @Autowired
    public UsersServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Users save(Users user) {
        repository.create(user);
        return user;
    }

    @Override
    public Optional<Users> findByUserId(String userId) {
        return repository.read(userId);
    }

    @Override
    public void update(String userId, String updatePassword) {
        repository.update(userId, updatePassword);
    }

    @Override
    public void delete(String userId) {
        repository.delete(userId);
    }
}
