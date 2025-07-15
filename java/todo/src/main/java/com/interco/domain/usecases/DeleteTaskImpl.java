package com.interco.domain.usecases;

import java.util.UUID;

import com.interco.domain.ports.TaskRepository;
import com.interco.domain.ports.usecases.DeleteTask;

public class DeleteTaskImpl implements DeleteTask {
    final private TaskRepository taskRepository;

    public DeleteTaskImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void execute(UUID id) {
        this.taskRepository.deleteById(id);
    }
}
