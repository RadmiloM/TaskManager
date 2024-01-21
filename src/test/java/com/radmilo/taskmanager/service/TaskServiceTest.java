package com.radmilo.taskmanager.service;

import com.radmilo.taskmanager.entity.Task;
import com.radmilo.taskmanager.exception.TaskNotFoundException;
import com.radmilo.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    TaskRepository taskRepository;
    @InjectMocks
    TaskService taskService;
    Task task;
    Long id;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setTitle("Learn");
        task.setDescription("Learn every day");
        id = 1L;
    }

    @Test
    void TaskService_SaveTask_ShouldSaveTask() {
        taskService.saveTask(task);
        verify(taskRepository).save(task);
    }

    @Test
    void TaskService_FetchTaskById_ShouldFindTaskWithProvidedId() {
        taskService.saveTask(task);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.ofNullable(task));
        taskService.fetchTaskById(id);
        verify(taskRepository).findById(anyLong());
    }

    @Test
    void TaskService_FetchTaskByIdWhenIdIsNotPresent_ShouldHandleMethodFetchTaskById() {
        when(taskRepository.findById(eq(22L))).thenReturn(Optional.ofNullable(null));
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.fetchTaskById(22L);
        });
        verify(taskRepository).findById(eq(22L));
    }

    @Test
    void TaskService_FetchTask_ShouldSuccessfullyFetchTasks() {
        taskService.saveTask(task);
        taskService.fetchTasks();
        verify(taskRepository).save(task);
        verify(taskRepository).findAll();
    }

    @Test
    void TaskService_DeleteTask_ShouldSuccessfullyDeleteTask() {
        taskService.saveTask(task);
        when(taskRepository.findById(id)).thenReturn(Optional.ofNullable(task));
        taskService.deleteTask(id);
        verify(taskRepository).save(task);
        verify(taskRepository).findById(id);
        verify(taskRepository).deleteById(id);
    }

    @Test
    void TaskService_DeleteTask_ShouldHandleMethodDeleteTask() {
        when(taskRepository.findById(eq(13L))).thenReturn(Optional.ofNullable(null));
        assertThrows(TaskNotFoundException.class,
                () -> taskService.deleteTask(13L));
        verify(taskRepository).findById(eq(13L));
    }

    @Test
    void TaskService_UpdateTask_ShouldSuccessfullyUpdateTask() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        taskService.updateTask(id, task);
        verify(taskRepository).save(task);
    }

    @Test
    void TaskService_UpdateTask_ShouldSuccessfullyHandleMethodUpdateTask() {
        when(taskRepository.findById(eq(14L))).thenReturn(Optional.ofNullable(null));
        assertThrows(TaskNotFoundException.class,
                () -> taskService.updateTask(14L, task));
        verify(taskRepository).findById(eq(14L));
    }

}