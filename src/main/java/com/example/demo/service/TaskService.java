package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Получить все задачи
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Получить задачу по ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Добавить или обновить задачу
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    // Удалить задачу
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}