package com.radmilo.taskmanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue
    Long id;

    private String title;

    private String description;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
