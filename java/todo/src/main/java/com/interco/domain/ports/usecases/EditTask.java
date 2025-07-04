package com.interco.domain.ports.usecases;

import com.interco.domain.entity.Task;

public interface EditTask {
    public Task execute(Task task);
}
