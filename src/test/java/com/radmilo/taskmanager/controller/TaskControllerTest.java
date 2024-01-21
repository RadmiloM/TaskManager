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

import static org.mockito.ArgumentMatchers.*;
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
    Long id;
    List<Task> tasks;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setTitle("Write");
        task.setDescription("Write every day");
        id = 1L;
        tasks = new ArrayList<>();
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
        when(taskService.fetchTaskById(id)).thenReturn(task);
        mockMvc.perform(get("/api/v1/tasks/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Write"))
                .andExpect(jsonPath("$.description").value("Write every day"));

    }

    @Test
    void TaskController_FetchTasks_ShouldReturnTasksSuccessfully() throws Exception {
        Task readingTask = new Task();
        readingTask.setTitle("Reading");
        readingTask.setDescription("Reading every day");
        tasks.add(task);
        tasks.add(readingTask);
        when(taskService.fetchTasks()).thenReturn(tasks);
        mockMvc.perform(get("/api/v1/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Write"))
                .andExpect(jsonPath("$[0].description").value("Write every day"))
                .andExpect(jsonPath("$[1].title").value("Reading"))
                .andExpect(jsonPath("$[1].description").value("Reading every day"));

    }

    @Test
    void TaskController_DeleteTaskById_ShouldSuccessfullyDeleteTaskById() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/{id}",id))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(taskService).deleteTask(id);
    }

    @Test
    void TaskController_UpdateTaskById_ShouldSuccessfullyUpdateTask() throws Exception {
        mockMvc.perform(put("/api/v1/tasks/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Watch\", \"description\": \"Watch tutorial every day\"}"))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(taskService).updateTask(anyLong(),any(Task.class));
    }

}