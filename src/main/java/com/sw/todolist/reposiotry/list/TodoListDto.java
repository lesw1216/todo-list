package com.sw.todolist.reposiotry.list;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class TodoListDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String content;

    @NotNull
    private Boolean completion;

    public TodoListDto() {
    }

    public TodoListDto(String userId, String content, Boolean completion) {
        this.userId = userId;
        this.content = content;
        this.completion = completion;
    }
}
