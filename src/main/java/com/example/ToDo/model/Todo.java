package com.example.ToDo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    private int id;

    private String title;

    private String description;

    private String status;

    private String priority;

    private LocalDate dueDate;

    private LocalDateTime createdAt;
}