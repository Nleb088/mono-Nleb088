package com.interco.domain.ports.usecases;

import java.util.List;
import java.util.UUID;

import com.interco.domain.entity.Task;

public interface ReorderTask {
    public List<Task> execute(long newPosition, UUID id);
}
