package com.interco.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class TaskJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "business_id", nullable = false, unique = true, length = 36)
    private String businessId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "position", nullable = false)
    private long position;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    public TaskJpaEntity() {
    }

    public TaskJpaEntity(String businessId, String title, long position, boolean completed) {
        this.businessId = businessId;
        this.title = title;
        this.position = position;
        this.completed = completed;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessId() {
        return this.businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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