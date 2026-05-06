package com.example.ToDo.service;



import com.example.ToDo.dto.TodoResponseDto;
import com.example.ToDo.entity.Todo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    public List<TodoResponseDto> getAllTodos() {

        List<Todo> todos = Arrays.asList(

                new Todo(
                        1,
                        "Learn Spring Boot",
                        "Complete REST API basics",
                        "IN_PROGRESS",
                        "HIGH",
                        "2026-05-10"
                ),

                new Todo(
                        2,
                        "Practice Git",
                        "Learn cherry-pick and merge",
                        "PENDING",
                        "MEDIUM",
                        "2026-05-15"
                ),

                new Todo(
                        3,
                        "Build ToDo App",
                        "Create sample ToDo project",
                        "COMPLETED",
                        "LOW",
                        "2026-05-01"
                )
        );

        return todos.stream()
                .map(todo -> new TodoResponseDto(
                        todo.getId(),
                        todo.getTitle(),
                        todo.getDescription(),
                        todo.getStatus(),
                        todo.getPriority(),
                        todo.getDueDate()
                ))
                .collect(Collectors.toList());
    }
}
