package com.interco.domain.entity;

import java.util.UUID;

public class Task {
    private final UUID id;
    private String title;
    private long position;
    private boolean completed;

    public Task(UUID id, String title, boolean completed, long position) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.position = position;
    }

    public Task(String title, long position) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.completed = false;
        this.position = position;
    }

    public UUID getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPosition() {
        return this.position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
