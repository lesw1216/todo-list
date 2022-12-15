package com.sw.todolist.controller;

import com.sw.todolist.domain.TodoList;
import com.sw.todolist.reposiotry.TodoListDto;
import com.sw.todolist.service.TodoListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @Autowired
    public listController(TodoListService service) {
        this.service = service;
    }


    /**
     * 전체 조회로 모든 리스트 보여주기
     */
    @GetMapping("/lists")
    public String todolistViewLists(Model model) {
        log.info("GET /lists");
        List<TodoList> lists = service.findByAll();
        model.addAttribute("lists", lists);

        return "pages/mainPage";
    }

    /**
     * 리스트 등록
     */
    @PostMapping("/lists")
    public String addList(@ModelAttribute TodoList list) {
        log.info("POST /lists");
        service.registerOfList(list);

        return "redirect:/lists";
    }

    @PutMapping("/lists/{listId}")
    public String putList(@PathVariable Long listId, @ModelAttribute TodoListDto listDto) {
        log.info("PUT /lists/{}", listId);
        service.modifyOfList(listId, listDto);

        return "redirect:/lists";
    }

    @DeleteMapping("/lists/{listId}")
    public String deleteList(@PathVariable Long listId) {
        log.info("DELETE /lists/{}", listId);
        service.deleteOfList(listId);

        return "redirect:/lists";
    }
}
