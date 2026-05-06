package com.example.ToDo.entity;

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

    private String dueDate;
}