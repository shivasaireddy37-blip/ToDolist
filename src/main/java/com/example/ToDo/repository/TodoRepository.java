package com.example.ToDo.repository;

import com.example.ToDo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository
        extends JpaRepository<Todo, Integer> {
}
