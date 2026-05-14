package com.example.ToDo.service;

import com.example.ToDo.dto.TodoRequestDto;
import com.example.ToDo.dto.TodoResponseDto;
import com.example.ToDo.entity.Todo;
import com.example.ToDo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoResponseDto getAllTodos() {

        List<TodoResponseDto.TodoData> todoList =
                todoRepository.getAllTodos()
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new TodoResponseDto(
                todoList.size(),
                todoList
        );
    }

    public TodoResponseDto.TodoData getTodoById(int id) {

        Todo todo = todoRepository.getTodoById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Todo record not found with id: " + id
                        ));

        return mapToResponse(todo);
    }

    public List<TodoResponseDto.TodoData> getTodoByTitle(
            String title) {

        return todoRepository.getTodoByTitle(title)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TodoResponseDto.TodoData addTodo(
            TodoRequestDto requestDto) {

        LocalDateTime createdAt = LocalDateTime.now();

        todoRepository.addTodo(
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getStatus(),
                requestDto.getPriority(),
                requestDto.getDueDate(),
                createdAt
        );

        List<Todo> todos = todoRepository.getAllTodos();

        Todo latestTodo = todos.get(todos.size() - 1);

        return mapToResponse(latestTodo);
    }

    public TodoResponseDto.TodoData updateTodo(
            int id,
            TodoRequestDto requestDto) {

        Todo existingTodo = todoRepository.getTodoById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Todo record not found with id: " + id
                        ));

        todoRepository.updateTodo(
                id,
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getStatus(),
                requestDto.getPriority(),
                requestDto.getDueDate()
        );

        existingTodo.setTitle(requestDto.getTitle());
        existingTodo.setDescription(requestDto.getDescription());
        existingTodo.setStatus(requestDto.getStatus());
        existingTodo.setPriority(requestDto.getPriority());
        existingTodo.setDueDate(requestDto.getDueDate());

        return mapToResponse(existingTodo);
    }

    public TodoResponseDto.TodoData patchTodo(
            int id,
            TodoRequestDto requestDto) {

        Todo todo = todoRepository.getTodoById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Todo record not found with id: " + id
                        ));

        String title = requestDto.getTitle() != null
                ? requestDto.getTitle()
                : todo.getTitle();

        String description = requestDto.getDescription() != null
                ? requestDto.getDescription()
                : todo.getDescription();

        String status = requestDto.getStatus() != null
                ? requestDto.getStatus()
                : todo.getStatus();

        String priority = requestDto.getPriority() != null
                ? requestDto.getPriority()
                : todo.getPriority();

        todoRepository.updateTodo(
                id,
                title,
                description,
                status,
                priority,
                requestDto.getDueDate() != null
                        ? requestDto.getDueDate()
                        : todo.getDueDate()
        );

        todo.setTitle(title);
        todo.setDescription(description);
        todo.setStatus(status);
        todo.setPriority(priority);

        if (requestDto.getDueDate() != null) {
            todo.setDueDate(requestDto.getDueDate());
        }

        return mapToResponse(todo);
    }

    public Map<String, String> deleteTodo(int id) {

        todoRepository.getTodoById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Todo record not found with id: " + id
                        ));

        todoRepository.deleteTodo(id);

        Map<String, String> response = new HashMap<>();

        response.put(
                "message",
                "Todo successfully deleted with id: " + id
        );

        return response;
    }

    private TodoResponseDto.TodoData mapToResponse(
            Todo todo) {

        return new TodoResponseDto.TodoData(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getStatus(),
                todo.getPriority(),
                todo.getDueDate(),
                todo.getCreatedAt()
        );
    }

    public Map<String, Object> getValidationErrorResponse(
            BindingResult bindingResult) {

        Map<String, String> validationErrors = new HashMap<>();

        for (FieldError error : bindingResult.getFieldErrors()) {

            validationErrors.put(
                    error.getField(),
                    error.getDefaultMessage()
            );
        }

        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("status", 400);
        errorResponse.put("message", "Validation failed");
        errorResponse.put("errors", validationErrors);

        return errorResponse;
    }

    public Map<String, Object> getNotFoundResponse(String message) {

        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("status", 404);
        errorResponse.put("message", message);

        return errorResponse;
    }
}