package com.radmilo.taskmanager.converter;

import com.radmilo.taskmanager.dto.TaskDTO;
import com.radmilo.taskmanager.entity.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskConverter {

    public Task mapToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setDescription(taskDTO.description());
        task.setTitle(taskDTO.title());
        return task;
    }

    public TaskDTO mapToDTO(Task task) {
        return new TaskDTO(task.getTitle(), task.getDescription());
    }

    public List<TaskDTO> mapToDTO(List<Task> tasks) {
        return tasks.stream().map(this::mapToDTO).toList();
    }
}
