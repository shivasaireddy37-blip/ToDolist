package com.example.ToDo.service;

import com.example.ToDo.constants.TodoConstants;
import com.example.ToDo.dto.TodoResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    public List<TodoResponseDto> getAllTodos() {

        return TodoConstants.TODOS.stream()
                .map(todo -> new TodoResponseDto(
                        todo.getId(),
                        todo.getTitle(),
                        todo.getDescription(),
                        todo.getStatus(),
                        todo.getPriority(),
                        todo.getDueDate(),
                        todo.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}
