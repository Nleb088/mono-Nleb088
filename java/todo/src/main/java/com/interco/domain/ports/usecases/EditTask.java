package com.interco.domain.ports.usecases;

import java.util.UUID;

import com.interco.domain.entity.EditTaskInput;
import com.interco.domain.entity.Task;

public interface EditTask {
    public Task execute(UUID taskId, EditTaskInput task);
}
