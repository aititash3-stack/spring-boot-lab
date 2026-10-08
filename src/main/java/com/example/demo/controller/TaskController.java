package com.example.demo.controller;

import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Показать список всех задач
    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "tasks";
    }

    // Добавить новую задачу
    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task) {
        task.setId(null);
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    // Удалить задачу
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    // Отметить задачу как выполненную
    @PostMapping("/complete/{id}")
    public String completeTask(@PathVariable Long id) {

        Task task = taskService.getTaskById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Задача не найдена"));

        task.setCompleted(true);
        taskService.saveTask(task);

        return "redirect:/tasks";
    }
}