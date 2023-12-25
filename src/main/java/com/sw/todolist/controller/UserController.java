package com.sw.todolist.controller;

import com.sw.todolist.domain.Users;
import com.sw.todolist.dto.UserDto;
import com.sw.todolist.service.users.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class UserController {

    private final UsersService usersService;

    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "pages/user/loginPage";
    }


    @GetMapping("/join")
    public String joinForm() {
        return "pages/user/joinPage";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute UserDto userDto) {
        Users user = new Users();
        user.setUserId(userDto.getUserId());
        user.setUserName(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        usersService.save(user);
        return "redirect:/login";
    }
}
