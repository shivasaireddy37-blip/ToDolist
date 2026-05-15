package com.example.ToDo.mapper;

import com.example.ToDo.entity.Todo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface TodoMapper {

    @Select("SELECT * FROM todo")
    List<Todo> getAllTodos();

    @Select("SELECT * FROM todo WHERE id = #{id}")
    Optional<Todo> getTodoById(int id);

    @Select("""
            SELECT * FROM todo
            WHERE REPLACE(LOWER(title), ' ', '')
            =
            REPLACE(LOWER(#{title}), ' ', '')
            """)
    List<Todo> getTodoByTitle(String title);

    @Insert("""
            INSERT INTO todo
            (title, description, status, priority, due_date, created_at)
            VALUES
            (#{title}, #{description}, #{status},
             #{priority}, #{dueDate}, #{createdAt})
            """)
    void addTodo(
            String title,
            String description,
            String status,
            String priority,
            LocalDate dueDate,
            LocalDateTime createdAt
    );

    @Update("""
            UPDATE todo
            SET title = #{title},
                description = #{description},
                status = #{status},
                priority = #{priority},
                due_date = #{dueDate}
            WHERE id = #{id}
            """)
    void updateTodo(
            int id,
            String title,
            String description,
            String status,
            String priority,
            LocalDate dueDate
    );

    @Delete("DELETE FROM todo WHERE id = #{id}")
    void deleteTodo(int id);
}