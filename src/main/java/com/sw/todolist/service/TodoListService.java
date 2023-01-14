package com.sw.todolist.service;

import com.sw.todolist.domain.TodoList;
import com.sw.todolist.reposiotry.list.TodoListDto;

import java.util.List;

/*
    등록
    수정
    삭제
    전체 조회
 */
public interface TodoListService {

    TodoList registerOfList(TodoList list);

    void modifyOfList(Long listId, TodoListDto list);

    List<TodoList> findByAll(String userId);

    void deleteOfList(Long listId);
}
