package com.sw.todolist.reposiotry;

import com.sw.todolist.domain.TodoList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemoryTodoListRepository implements TodoListRepository{

    private static final Map<Long, TodoList> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public TodoList create(TodoList list) {
        list.setId(++sequence);
        store.put(list.getId(), list);
        return list;
    }

    @Override
    public Optional<TodoList> read(Long listId) {
        return Optional.ofNullable(store.get(listId));
    }

    @Override
    public List<TodoList> readOfListAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Long listId, TodoListDto list) {
        TodoList findList = store.get(listId);

        findList.setContent(list.getContent());
        findList.setCompletion(list.getCompletion());
    }

    @Override
    public void delete(Long listId) {
        TodoList removeList = store.remove(listId);
        log.info("[DELETE LIST]=[{}]", removeList);
    }

    public void clear() {
        store.clear();
        sequence = 0L;
    }
}
