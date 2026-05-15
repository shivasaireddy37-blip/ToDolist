package com.example.ToDo.constants;

import com.example.ToDo.entity.Todo;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoConstants {

    public  List<Todo> TODOS = new ArrayList<>();

     {

        TODOS.add(
                new Todo(
                        1,
                        "Spring",
                        "Complete REST API basics",
                        "IN_PROGRESS",
                        "HIGH",
                        LocalDate.parse("2026-05-10"),
                        LocalDateTime.of(2026, 5, 6, 10, 30, 0)
                )
        );

        TODOS.add(
                new Todo(
                        2,
                        "Practice",
                        "Learn cherry-pick and merge",
                        "PENDING",
                        "MEDIUM",
                        LocalDate.parse("2026-05-10"),
                        LocalDateTime.of(2026, 5, 6, 11, 45, 30)
                )
        );

        TODOS.add(
                new Todo(
                        3,
                        "ToDo",
                        "Create sample ToDo project",
                        "COMPLETED",
                        "LOW",
                        LocalDate.parse("2026-05-10"),
                        LocalDateTime.of(2026, 5, 6, 9, 15, 15)
                )
        );
}
    

    public TodoConstants() {
    }
}