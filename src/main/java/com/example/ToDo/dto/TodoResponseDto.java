package com.example.ToDo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDto {

    private int id;

    private String title;

    private String description;

    private String status;

    private String priority;

    private String dueDate;

    private LocalDateTime createdAt;
    
}