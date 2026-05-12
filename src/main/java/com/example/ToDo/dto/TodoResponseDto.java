package com.example.ToDo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDto {

    private int totalCount;

    private List<TodoData> todos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TodoData {

        private int id;

        private String title;

        private String description;

        private String status;

        private String priority;

        private String dueDate;

        private LocalDateTime createdAt;
    }
}