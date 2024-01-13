package com.radmilo.taskmanager.command;

import com.radmilo.taskmanager.entity.Task;
import com.radmilo.taskmanager.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TaskCommand implements CommandLineRunner {
    private final TaskRepository taskRepository;

    public TaskCommand(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        taskRepository.save(new Task("Homework", "Do the homework"));
        taskRepository.save(new Task("Read", "Read every day"));
        taskRepository.save(new Task("Learn", "Learn every day"));
    }
}
