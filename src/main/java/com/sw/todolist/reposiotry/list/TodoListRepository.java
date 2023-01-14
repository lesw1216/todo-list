package com.sw.todolist.reposiotry.list;

import com.sw.todolist.domain.TodoList;
import com.sw.todolist.reposiotry.list.TodoListDto;

import java.util.List;
import java.util.Optional;

/**
 * 생성
 * 수정
 * 삭제
 * 조회
 */
public interface TodoListRepository {

    TodoList create(TodoList list);

    Optional<TodoList> read(Long listId);

    List<TodoList> readOfListAll(String userId);

    void update(Long listId, TodoListDto list);

    void delete(Long listId);
}
