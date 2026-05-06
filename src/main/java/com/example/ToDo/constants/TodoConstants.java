package com.example.ToDo.constants;

import com.example.ToDo.entity.Todo;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TodoConstants {

    public static final List<Todo> TODOS = Arrays.asList(

            new Todo(
                    1,
                    "Learn Spring Boot",
                    "Complete REST API basics",
                    "IN_PROGRESS",
                    "HIGH",
                    "2026-05-10",
                    LocalDateTime.of(2026, 5, 6, 10, 30, 0)
            ),

            new Todo(
                    2,
                    "Practice Git",
                    "Learn cherry-pick and merge",
                    "PENDING",
                    "MEDIUM",
                    "2026-05-15",
                    LocalDateTime.of(2026, 5, 6, 11, 45, 30)
            ),

            new Todo(
                    3,
                    "Build ToDo App",
                    "Create sample ToDo project",
                    "COMPLETED",
                    "LOW",
                    "2026-05-01",
                    LocalDateTime.of(2026, 5, 6, 9, 15, 15)
            )
    );

    private TodoConstants() {
    }
}