package com.radmilo.taskmanager.controller;

import com.radmilo.taskmanager.converter.TaskConverter;
import com.radmilo.taskmanager.dto.TaskDTO;
import com.radmilo.taskmanager.entity.Task;
import com.radmilo.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @MockBean
    private TaskService taskService;
    @MockBean
    private TaskConverter taskConverter;
    @Autowired
    private MockMvc mockMvc;
    Task task;
    Task readingTask;
    Long id;
    List<Task> tasks;
    TaskDTO taskDTO;
    List<TaskDTO> taskDTOS;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setTitle("Write");
        task.setDescription("Write every day");
        readingTask = new Task();
        readingTask.setTitle("Reading");
        readingTask.setDescription("Reading every day");
        id = 1L;
        tasks = new ArrayList<>();
        taskDTO = new TaskDTO("Work", "Work every day");
        taskDTOS = new ArrayList<>();
    }

    @Test
    void TaskController_SaveTask_ShouldSuccessfullySaveTask() throws Exception {
        when(taskConverter.mapToEntity(any(TaskDTO.class))).thenReturn(task);
        when(taskService.saveTask(any(Task.class))).thenReturn(task);
        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Listen\", " +
                                "\"description\": \"Listen every day\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/tasks/"));
    }

    @Test
    void TaskController_FetchTaskById_ShouldFindTaskWithId() throws Exception {
        when(taskService.fetchTaskById(anyLong())).thenReturn(task);
        when(taskConverter.mapToDTO(task)).thenReturn(taskDTO);
        mockMvc.perform(get("/api/v1/tasks/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Work"))
                .andExpect(jsonPath("$.description").value("Work every day"));

    }

    @Test
    void TaskController_FetchTasks_ShouldReturnTasksSuccessfully() throws Exception {
        tasks.add(task);
        tasks.add(readingTask);
        taskDTOS.add(taskDTO);
        when(taskService.fetchTasks()).thenReturn(tasks);
        when(taskConverter.mapToDTO(tasks)).thenReturn(taskDTOS);
        mockMvc.perform(get("/api/v1/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Work"))
                .andExpect(jsonPath("$[0].description").value("Work every day"));

    }

    @Test
    void TaskController_DeleteTaskById_ShouldSuccessfullyDeleteTaskById() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(taskService).deleteTask(id);
    }

    @Test
    void TaskController_UpdateTaskById_ShouldSuccessfullyUpdateTask() throws Exception {
        mockMvc.perform(put("/api/v1/tasks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Watch\", \"description\": \"Watch tutorial every day\"}"))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(taskService).updateTask(anyLong(), any(Task.class));
    }

}