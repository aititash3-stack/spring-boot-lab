package com.example.demo.controller;

import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Показать все задачи
    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "tasks";
    }

    // Открыть форму создания новой задачи
    @GetMapping("/new")
    public String newTask(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";
    }

    // Открыть форму редактирования задачи
    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {

        Task task = taskService.getTaskById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Задача не найдена"));

        model.addAttribute("task", task);
        return "task-form";
    }

    // Сохранить новую или отредактированную задачу
    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") Task task) {

        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    // Добавление задачи через существующую форму
    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task) {

        task.setId(null);
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    // Фильтрация задач
    @GetMapping("/filter")
    public String filterTasks(@RequestParam boolean completed,
                              Model model) {

        List<Task> filteredTasks =
                taskService.findByCompleted(completed);

        model.addAttribute("tasks", filteredTasks);

        return "tasks";
    }

    // Удаление задачи
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