package com.radmilo.taskmanager.service;

import com.radmilo.taskmanager.entity.Task;
import com.radmilo.taskmanager.exception.TaskNotFoundException;
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
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()){
            throw new TaskNotFoundException("Task with id: " + id + " was not found");
        }
        return optionalTask.get();
    }

    public List<Task> fetchTasks(){
        return taskRepository.findAll();
    }

    public void deleteTask(Long id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()){
            throw new TaskNotFoundException("Task with id: " + id + " was not found");
        }
        taskRepository.deleteById(id);
    }

    public void updateTask(Long id, Task updatedTask){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isEmpty()){
            throw new TaskNotFoundException("Task with id: " + id + " was not found");
        }
        Task foundTask = optionalTask.get();
        if(updatedTask.getTitle() != null && !updatedTask.getTitle().equals("")){
            foundTask.setTitle(updatedTask.getTitle());
        }
        if(updatedTask.getDescription() != null && !updatedTask.getDescription().equals("")){
            foundTask.setDescription(updatedTask.getDescription());
        }
        taskRepository.save(foundTask);

    }

}
