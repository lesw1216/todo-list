package com.sw.todolist.reposiotry;

import com.sw.todolist.domain.TodoList;
import com.sw.todolist.reposiotry.list.TodoListDto;
import com.sw.todolist.reposiotry.list.TodoListRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
@Transactional
class MemoryTodoListRepositoryTest {

    @Autowired
    TodoListRepository repository;

    /*@BeforeEach
    void before() {
        if (repository instanceof MemoryTodoListRepository) {
            ((MemoryTodoListRepository) repository).clear();
        }
    }*/

    @Test
    void create() {
        // 저장
        TodoList newList = new TodoList(1L, "hong","운동하기", false);
        repository.create(newList);

        // 저장된 값 꺼내기
        Optional<TodoList> todoList = repository.read(1L);

        // 검증
        Assertions.assertThat(todoList.orElse(null)).isEqualTo(newList);
    }

    @Test
    void update() {
        TodoList newList = new TodoList(1L, "hong", "운동하기", false);
        repository.create(newList);

        // 수정 내용
        String updateContent = "헬스장 가기";
        Boolean completion = true;

        // 수정
        log.info("updateContent={}", updateContent);
        repository.update(1L, new TodoListDto("hong", updateContent, completion));

        // 수정 되었는지 검증
        Optional<TodoList> readList = repository.read(1L);
        log.info("readList.getContent={}", readList.get().getContent());

        Assertions.assertThat(readList.map(TodoList::getContent).orElse(null)).isEqualTo(updateContent);
        Assertions.assertThat(readList.map(TodoList::getCompletion).orElse(false)).isEqualTo(completion);
    }

    @Test
    void findById() {
        TodoList listA = new TodoList(1L, "hong", "운동하기", false);

        repository.create(listA);
        Optional<TodoList> findList = repository.read(1L);

        Assertions.assertThat(findList.get()).isEqualTo(listA);
    }

    @Test
    void delete() {
        TodoList listA = new TodoList(1L, "hong", "운동하기", true);
        repository.create(listA);

        // 삭제
        repository.delete(1L);

        // 검증
        Optional<TodoList> findList = repository.read(1L);
        Assertions.assertThat(findList.orElse(null)).isNull();
    }

    @Test
    void findAll() {
        TodoList listA = new TodoList(1L, "hong", "운동하기", false);
        TodoList listB = new TodoList(1L, "hong", "헬스 하기", true);

        repository.create(listA);
        repository.create(listB);

        List<TodoList> lists = repository.readOfListAll("hong");
        Assertions.assertThat(lists).containsExactly(listA, listB);
    }
}