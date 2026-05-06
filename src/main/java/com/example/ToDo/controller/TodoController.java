package com.example.ToDo.controller;

import com.example.ToDo.dto.TodoResponseDto;
import com.example.ToDo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/getAllTodos")
    public List<TodoResponseDto> getTodos() {
        return todoService.getAllTodos();
    }


}
