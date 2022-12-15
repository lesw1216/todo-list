package com.sw.todolist.reposiotry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class TodoListDto {
    private String content;
    private Boolean completion;

    public TodoListDto() {
    }

    public TodoListDto(String content, Boolean completion) {
        this.content = content;
        this.completion = completion;
    }
}
