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

    public long setPosition(long position) {
        return this.position = position;
    }

    public long getPosition() {
        return this.position;
    }

    public boolean isCompleted() {
        return this.completed;
    }

}
