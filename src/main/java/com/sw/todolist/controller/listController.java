package com.sw.todolist.controller;

import com.sw.todolist.domain.TodoList;
import com.sw.todolist.domain.Users;
import com.sw.todolist.reposiotry.list.TodoListDto;
import com.sw.todolist.service.TodoListService;
import com.sw.todolist.service.users.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * POST - 할일 작성 후 저장 요청
 * GET - /lists, 전체 할일 리스트 요청
 * PUT - /lists/{id} 할일 수정
 * DELETE /lists/{id} 삭제
 * <p>
 * 단건 조회는 딱히?....
 */
@Slf4j
@Controller
public class listController {

    private final TodoListService service;
    private final UsersService usersService;


    @Autowired
    public listController(TodoListService service, UsersService usersService) {
        this.service = service;
        this.usersService = usersService;
    }


    /**
     * 전체 조회로 모든 리스트 보여주기
     */
    @GetMapping("/lists")
    public String todolistViewLists(@AuthenticationPrincipal User user, Model model) {
        log.info("[GET][/lists]");

        String AuthenticationUserId = user.getUsername();
        Optional<Users> loginUser = usersService.findByUserId(AuthenticationUserId);
        String name = loginUser.get().getUserName();
        log.info("이름:[{}]", name);

        // 전체 할일 목록을 꺼내와서 모델에 담아 준다.
        List<TodoList> lists = service.findByAll(AuthenticationUserId);
        model.addAttribute("lists", lists);

        model.addAttribute("username", name);
        model.addAttribute("inputList", new TodoListDto(AuthenticationUserId, null, false));

        return "pages/mainPage";
    }

    /**
     * 리스트 등록
     */
    @PostMapping("/lists")
    public String addList(@Validated @ModelAttribute("inputList") TodoListDto list,
                          BindingResult bindingResult,
                          @AuthenticationPrincipal User user,
                          Model model) {
        log.info("[POST][/lists]");
        log.info("username ={}", user.getUsername());
        // 검증 처리
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            List<TodoList> lists = service.findByAll(user.getUsername());
            model.addAttribute("lists", lists);
            return "pages/mainPage";
        }

        TodoList addList = new TodoList(null, list.getUserId(), list.getContent(), list.getCompletion());
        service.registerOfList(addList);

        return "redirect:/lists";
    }

    /**
       할일 수정
     */
    @PutMapping("/lists/{listId}")
    public String putList(@PathVariable Long listId,
                          @ModelAttribute TodoListDto listDto) {

        log.info("[PUT][/lists/{}][USERID={}]", listId, listDto.getUserId());
        service.modifyOfList(listId, listDto);


        return "redirect:/lists";
    }

    @DeleteMapping("/lists/{listId}")
    public String deleteList(@PathVariable Long listId) {
        log.info("[DELETE][/lists/{}]", listId);
        service.deleteOfList(listId);

        return "redirect:/lists";
    }
}
