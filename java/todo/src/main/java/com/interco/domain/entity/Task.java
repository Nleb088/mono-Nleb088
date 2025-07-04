package com.interco.domain.entity;

import java.util.UUID;

public class Task {
    private final UUID id;
    private String title;
    private Integer position;
    private boolean completed;

    public Task(String title, Integer position) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.completed = false;
        this.position = position;
    }

    public void complete() {
        if (this.completed) {
            return;
        }
        this.completed = true;
    }

    public void uncomplete() {
        if (!this.completed) {
            return;
        }
        this.completed = false;
    }

    public UUID getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public Integer setPosition(Integer position) {
        return this.position = position;
    }

    public Integer getPosition() {
        return this.position;
    }

    public boolean isCompleted() {
        return this.completed;
    }

}
