package com.sw.todolist.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class TodoList {
    private Long id;
    private String userId;
    private String content;
    private Boolean completion;

    public TodoList() {
    }

    public TodoList(Long id, String userId, String content, Boolean completion) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.completion = completion;
    }
}
