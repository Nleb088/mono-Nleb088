package com.interco.domain.ports.usecases;

import java.util.List;

import com.interco.domain.entity.Task;

public interface GetAllTasks {
    public List<Task> execute();
}
