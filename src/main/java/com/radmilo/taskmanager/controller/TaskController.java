package com.radmilo.taskmanager.controller;

import com.radmilo.taskmanager.converter.TaskConverter;
import com.radmilo.taskmanager.dto.TaskDTO;
import com.radmilo.taskmanager.entity.Task;
import com.radmilo.taskmanager.service.TaskService;
import jakarta.validation.Valid;
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
    private final TaskConverter taskConverter;

    public TaskController(TaskService taskService, TaskConverter taskConverter) {
        this.taskService = taskService;
        this.taskConverter = taskConverter;
    }

    @PostMapping(path = "/tasks")
    public ResponseEntity<Void> saveTask(@Valid @RequestBody TaskDTO taskDTO) {
        Task task = taskConverter.mapToEntity(taskDTO);
        Task savedTask = taskService.saveTask(task);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTask.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/tasks/{id}")
    public ResponseEntity<TaskDTO> fetchTaskById(@PathVariable Long id) {
        Task task = taskService.fetchTaskById(id);
        TaskDTO taskDTO = taskConverter.mapToDTO(task);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/tasks")
    public ResponseEntity<List<TaskDTO>> fetchTasks() {
        List<Task> tasks = taskService.fetchTasks();
        List<TaskDTO> taskDTOS = taskConverter.mapToDTO(tasks);
        return new ResponseEntity<>(taskDTOS, HttpStatus.OK);
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
