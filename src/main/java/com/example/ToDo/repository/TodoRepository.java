package com.example.ToDo.repository;

import com.example.ToDo.entity.Todo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoRepository
        extends JpaRepository<Todo, Integer> {

    @Query(
            value = "SELECT * FROM todo",
            nativeQuery = true
    )
    List<Todo> getAllTodos();

    @Query(
            value = "SELECT * FROM todo WHERE id = :id",
            nativeQuery = true
    )
    Optional<Todo> getTodoById(int id);

    @Query(
            value = """
                    SELECT * FROM todo
                    WHERE REPLACE(LOWER(title), ' ', '')
                    =
                    REPLACE(LOWER(:title), ' ', '')
                    """,
            nativeQuery = true
    )
    List<Todo> getTodoByTitle(String title);

    @Transactional
    @Modifying
    @Query(
            value = """
                    INSERT INTO todo
                    (title, description, status, priority, due_date, created_at)
                    VALUES
                    (:title, :description, :status, :priority, :dueDate, :createdAt)
                    """,
            nativeQuery = true
    )
    void addTodo(
            String title,
            String description,
            String status,
            String priority,
            LocalDate dueDate,
            LocalDateTime createdAt
    );

    @Transactional
    @Modifying
    @Query(
            value = """
                    UPDATE todo
                    SET title = :title,
                        description = :description,
                        status = :status,
                        priority = :priority,
                        due_date = :dueDate
                    WHERE id = :id
                    """,
            nativeQuery = true
    )
    void updateTodo(
            int id,
            String title,
            String description,
            String status,
            String priority,
            LocalDate dueDate
    );

    @Transactional
    @Modifying
    @Query(
            value = "DELETE FROM todo WHERE id = :id",
            nativeQuery = true
    )
    void deleteTodo(int id);
}