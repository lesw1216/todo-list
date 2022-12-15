package com.sw.todolist.service;

import com.sw.todolist.domain.TodoList;
import com.sw.todolist.reposiotry.TodoListDto;
import com.sw.todolist.reposiotry.TodoListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TodoListServiceImpl implements TodoListService{

    private final TodoListRepository repository;

    @Autowired
    public TodoListServiceImpl(TodoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public TodoList registerOfList(TodoList list) {
        return repository.create(list);
    }

    @Override
    public void modifyOfList(Long listId, TodoListDto list) {
        repository.update(listId, list);
    }

    @Override
    public List<TodoList> findByAll() {
        return repository.readOfListAll();
    }

    @Override
    public void deleteOfList(Long listId) {
        repository.delete(listId);
    }
}
