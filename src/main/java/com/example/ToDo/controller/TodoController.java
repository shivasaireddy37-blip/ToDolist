package com.example.ToDo.controller;

import com.example.ToDo.dto.TodoRequestDto;
import com.example.ToDo.dto.TodoResponseDto;
import com.example.ToDo.model.GenericResponse;
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
    public ResponseEntity<GenericResponse<TodoResponseDto>>
    getTodos() {

        TodoResponseDto response =
                todoService.getAllTodos();

        return ResponseEntity.ok(
                new GenericResponse<>(
                        200,
                        "Todos fetched successfully",
                        response
                )
        );
    }

    @GetMapping("/getToDoById/{id}")
    public ResponseEntity<GenericResponse<?>>
    getTodoById(@PathVariable int id) {

        try {

            return ResponseEntity.ok(
                    new GenericResponse<>(
                            200,
                            "Todo fetched successfully",
                            todoService.getTodoById(id)
                    )
            );

        } catch (RuntimeException ex) {

            return ResponseEntity.status(404)
                    .body(
                            new GenericResponse<>(
                                    404,
                                    ex.getMessage(),
                                    null
                            )
                    );
        }
    }

    @GetMapping("/getToDoByTitle/{title}")
    public ResponseEntity<GenericResponse<?>>
    getTodoByTitle(
            @PathVariable
            @NotBlank(message = "Title cannot be empty")
            String title) {

        var todos = todoService.getTodoByTitle(title);

        if (todos.isEmpty()) {

            return ResponseEntity.status(404)
                    .body(
                            new GenericResponse<>(
                                    404,
                                    "Todo not found with title: " + title,
                                    null
                            )
                    );
        }

        return ResponseEntity.ok(
                new GenericResponse<>(
                        200,
                        "Todos fetched successfully",
                        todos
                )
        );
    }

    @PostMapping("/createToDo")
    public ResponseEntity<GenericResponse<?>>
    addTodo(
            @Valid @RequestBody TodoRequestDto requestDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return ResponseEntity.badRequest()
                    .body(
                            new GenericResponse<>(
                                    400,
                                    "Validation failed",
                                    todoService
                                            .getValidationErrorResponse(
                                                    bindingResult
                                            )
                            )
                    );
        }

        return ResponseEntity.status(201)
                .body(
                        new GenericResponse<>(
                                201,
                                "Todo created successfully",
                                todoService.addTodo(requestDto)
                        )
                );
    }

    @PutMapping("/updateToDo/{id}")
    public ResponseEntity<GenericResponse<?>>
    updateTodo(
            @PathVariable int id,
            @Valid @RequestBody TodoRequestDto requestDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return ResponseEntity.badRequest()
                    .body(
                            new GenericResponse<>(
                                    400,
                                    "Validation failed",
                                    todoService
                                            .getValidationErrorResponse(
                                                    bindingResult
                                            )
                            )
                    );
        }

        try {

            return ResponseEntity.ok(
                    new GenericResponse<>(
                            200,
                            "Todo updated successfully",
                            todoService.updateTodo(id, requestDto)
                    )
            );

        } catch (RuntimeException ex) {

            return ResponseEntity.status(404)
                    .body(
                            new GenericResponse<>(
                                    404,
                                    ex.getMessage(),
                                    null
                            )
                    );
        }
    }

    @DeleteMapping("/deleteToDo/{id}")
    public ResponseEntity<GenericResponse<?>>
    deleteTodo(@PathVariable int id) {

        try {

            todoService.deleteTodo(id);

            return ResponseEntity.ok(
                    new GenericResponse<>(
                            200,
                            "Todo deleted successfully",
                            null
                    )
            );

        } catch (RuntimeException ex) {

            return ResponseEntity.status(404)
                    .body(
                            new GenericResponse<>(
                                    404,
                                    ex.getMessage(),
                                    null
                            )
                    );
        }
    }

    @PatchMapping("/partialUpdateToDo/{id}")
    public ResponseEntity<GenericResponse<?>>
    patchTodo(
            @PathVariable int id,
            @RequestBody TodoRequestDto requestDto) {

        try {

            return ResponseEntity.ok(
                    new GenericResponse<>(
                            200,
                            "Todo partially updated successfully",
                            todoService.patchTodo(id, requestDto)
                    )
            );

        } catch (RuntimeException ex) {

            return ResponseEntity.status(404)
                    .body(
                            new GenericResponse<>(
                                    404,
                                    ex.getMessage(),
                                    null
                            )
                    );
        }
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GenericResponse<?>>
    handleTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        return ResponseEntity.badRequest()
                .body(
                        new GenericResponse<>(
                                400,
                                "Invalid ID. Please provide a numeric value.",
                                null
                        )
                );
    }
}