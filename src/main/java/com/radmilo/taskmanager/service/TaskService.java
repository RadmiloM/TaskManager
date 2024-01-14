package com.radmilo.taskmanager.service;

import com.radmilo.taskmanager.entity.Task;
import com.radmilo.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Task fetchTaskById(Long id) {
        return taskRepository.findById(id).get();
    }

    public List<Task> fetchTasks(){
        return taskRepository.findAll();
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

    public void updateTask(Long id, Task updatedTask){
        Optional<Task> taskDB = taskRepository.findById(id);
        Task foundTask = taskDB.get();
        if(updatedTask.getTitle() != null && !updatedTask.getTitle().equals("")){
            foundTask.setTitle(updatedTask.getTitle());
        }
        if(updatedTask.getDescription() != null && !updatedTask.getDescription().equals("")){
            foundTask.setDescription(updatedTask.getDescription());
        }
        taskRepository.save(foundTask);

    }

}
