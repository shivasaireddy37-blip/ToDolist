package com.example.ToDo.controller;

import com.example.ToDo.dto.TodoRequestDto;
import com.example.ToDo.dto.TodoResponseDto;
import com.example.ToDo.service.TodoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/getAllToDos")
    public TodoResponseDto getTodos() {

        return todoService.getAllTodos();
    }

    @GetMapping("/getToDoById/{id}")
    public ResponseEntity<Object> getTodoById(
            @PathVariable int id) {

        try {

            return ResponseEntity.ok(
                    todoService.getTodoById(id));

        } catch (RuntimeException ex) {

            return ResponseEntity.status(404)
                    .body(
                            todoService.getNotFoundResponse(
                                    ex.getMessage()));
        }
    }

    @GetMapping("/getToDoByTitle/{title}")
    public ResponseEntity<Object> getTodoByTitle(
            @PathVariable @NotBlank(message = "Title cannot be empty") String title) {

        List<TodoResponseDto.TodoData> todos = todoService.getTodoByTitle(title);

        if (todos.isEmpty()) {

            return ResponseEntity.status(404)
                    .body(
                            todoService.getNotFoundResponse(
                                    "Todo not found with title: " + title));
        }

        return ResponseEntity.ok(todos);
    }

    @PostMapping("/createToDo")
    public ResponseEntity<Object> addTodo(
            @Valid @RequestBody TodoRequestDto requestDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return ResponseEntity.badRequest()
                    .body(todoService
                            .getValidationErrorResponse(bindingResult));
        }

        return ResponseEntity.status(201)
                .body(todoService.addTodo(requestDto));
    }

    @PutMapping("/updateToDo/{id}")
    public ResponseEntity<Object> updateTodo(
            @PathVariable int id,
            @Valid @RequestBody TodoRequestDto requestDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return ResponseEntity.badRequest()
                    .body(todoService
                            .getValidationErrorResponse(bindingResult));
        }

        try {

            return ResponseEntity.ok(
                    todoService.updateTodo(id, requestDto));

        } catch (RuntimeException ex) {

            return ResponseEntity.status(404)
                    .body(todoService
                            .getNotFoundResponse(ex.getMessage()));
        }
    }

    @DeleteMapping("/deleteToDo/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable int id) {

        try {

            return ResponseEntity.ok(
                    todoService.deleteTodo(id));

        } catch (RuntimeException ex) {

            return ResponseEntity.status(404)
                    .body(todoService
                            .getNotFoundResponse(ex.getMessage()));
        }
    }

    @PatchMapping("/partialUpdateToDo/{id}")
    public ResponseEntity<Object> patchTodo(
            @PathVariable int id,
            @RequestBody TodoRequestDto requestDto) {

        try {

            return ResponseEntity.ok(
                    todoService.patchTodo(id, requestDto));
        } catch (RuntimeException ex) {

            return ResponseEntity.status(404)
                    .body(todoService
                            .getNotFoundResponse(ex.getMessage()));
        }
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("status", 400);
        errorResponse.put("message", "Invalid ID. Please provide a numeric value.");

        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

}
