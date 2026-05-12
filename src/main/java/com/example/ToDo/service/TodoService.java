package com.example.ToDo.service;

import com.example.ToDo.constants.TodoConstants;
import com.example.ToDo.dto.TodoRequestDto;
import com.example.ToDo.dto.TodoResponseDto;
import com.example.ToDo.entity.Todo;
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

    private final TodoConstants todoConstants = new TodoConstants();

    public TodoResponseDto getAllTodos() {

        List<TodoResponseDto.TodoData> todoList =
                todoConstants.TODOS.stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new TodoResponseDto(
                todoList.size(),
                todoList
        );
    }

    public TodoResponseDto.TodoData getTodoById(int id) {

        Todo todo = todoConstants.TODOS.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Todo record not found with id: " + id
                        ));

        return mapToResponse(todo);
    }

    public List<TodoResponseDto.TodoData> getTodoByTitle(String title) {

        String formattedTitle = title
                .replaceAll("\\s+", "")
                .trim();

        return todoConstants.TODOS.stream()
                .filter(todo ->
                        todo.getTitle() != null &&
                                todo.getTitle()
                                        .replaceAll("\\s+", "")
                                        .trim()
                                        .equalsIgnoreCase(formattedTitle))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TodoResponseDto.TodoData addTodo(
            TodoRequestDto requestDto) {

        int newId = todoConstants.TODOS.stream()
                .mapToInt(Todo::getId)
                .max()
                .orElse(0) + 1;

        Todo todo = new Todo(
                newId,
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getStatus(),
                requestDto.getPriority(),
                requestDto.getDueDate(),
                LocalDateTime.now()
        );

        todoConstants.TODOS.add(todo);

        return mapToResponse(todo);
    }

    public TodoResponseDto.TodoData updateTodo(
            int id,
            TodoRequestDto requestDto) {

        Todo todo = todoConstants.TODOS.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Todo record not found with id: " + id
                        ));

        todo.setTitle(requestDto.getTitle());
        todo.setDescription(requestDto.getDescription());
        todo.setStatus(requestDto.getStatus());
        todo.setPriority(requestDto.getPriority());
        todo.setDueDate(requestDto.getDueDate());

        return mapToResponse(todo);
    }

    public TodoResponseDto.TodoData patchTodo(
            int id,
            TodoRequestDto requestDto) {

        Todo todo = todoConstants.TODOS.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Todo record not found with id: " + id
                        ));

        if (requestDto.getTitle() != null) {
            todo.setTitle(requestDto.getTitle());
        }

        if (requestDto.getDescription() != null) {
            todo.setDescription(requestDto.getDescription());
        }

        if (requestDto.getStatus() != null) {
            todo.setStatus(requestDto.getStatus());
        }

        if (requestDto.getPriority() != null) {
            todo.setPriority(requestDto.getPriority());
        }

        if (requestDto.getDueDate() != null) {
            todo.setDueDate(requestDto.getDueDate());
        }

        return mapToResponse(todo);
    }

    public Map<String, String> deleteTodo(int id) {

        Todo todo = todoConstants.TODOS.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Todo record not found with id: " + id
                        ));

        todoConstants.TODOS.remove(todo);

        Map<String, String> response = new HashMap<>();

        response.put(
                "message",
                "Todo successfully deleted with id: " + id
        );

        return response;
    }

    private TodoResponseDto.TodoData mapToResponse(Todo todo) {

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