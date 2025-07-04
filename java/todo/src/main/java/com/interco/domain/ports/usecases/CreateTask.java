package com.interco.domain.ports.usecases;

import com.interco.domain.entity.Task;

public interface CreateTask {
    public Task execute(String title);
}
