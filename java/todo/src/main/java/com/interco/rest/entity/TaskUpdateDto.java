package com.interco.rest.entity;

import java.util.Optional;

public class TaskUpdateDto {
    private final Optional<String> title;
    private final Optional<Boolean> completed;

    public TaskUpdateDto(Optional<String> title, Optional<Boolean> completed) {
        this.title = title;
        this.completed = completed;
    }

    public Optional<String> getTitle() {
        return this.title;
    }

    public Optional<Boolean> getCompleted() {
        return this.completed;
    }
}
