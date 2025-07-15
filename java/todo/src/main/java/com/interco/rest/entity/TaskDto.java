package com.interco.rest.entity;

public class TaskDto {
    private final String id;
    private final String title;
    private final long position;
    private final boolean completed;

    public TaskDto(String id, String title, long position, boolean completed) {
        this.id = id;
        this.title = title;
        this.position = position;
        this.completed = completed;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public long getPosition() {
        return this.position;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public boolean getCompleted() {
        return this.completed;
    }

}
