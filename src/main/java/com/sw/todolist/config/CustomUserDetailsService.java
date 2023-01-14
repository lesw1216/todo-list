package com.sw.todolist.config;

import com.sw.todolist.domain.Users;
import com.sw.todolist.reposiotry.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("userId={}", username);
        Optional<Users> OptionalUser = repository.read(username);
        if (OptionalUser.isEmpty()) {
            throw new UsernameNotFoundException("user not found username = " + username);
        }

        Users findUser = OptionalUser.get();

        return User.builder()
                .username(findUser.getUserId())
                .password(findUser.getPassword())
                .authorities("USER").build();
    }
}
