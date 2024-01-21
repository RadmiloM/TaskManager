package com.radmilo.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskDTO(@NotBlank(message = "Title may not be null or empty")String title
        ,@NotBlank(message="Description may not be null or empty")String description) {
}
