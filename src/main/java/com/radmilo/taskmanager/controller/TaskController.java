package com.radmilo.taskmanager.controller;

import com.radmilo.taskmanager.entity.Task;
import com.radmilo.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(path = "/tasks")
    public ResponseEntity<Void> saveTask(@RequestBody Task task) {
        Task savedTask = taskService.saveTask(task);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTask.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/tasks/{id}")
    public ResponseEntity<Task> fetchTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.fetchTaskById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/tasks")
    public ResponseEntity<List<Task>> fetchTasks() {
        return new ResponseEntity<>(taskService.fetchTasks(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/tasks/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        taskService.updateTask(id, updatedTask);
        return ResponseEntity.noContent().build();
    }
}