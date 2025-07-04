package com.interco.domain.usecases;

import java.util.List;

import com.interco.domain.entity.Task;
import com.interco.domain.ports.TaskRepository;
import com.interco.domain.ports.usecases.GetAllTasks;

public class GetAllTasksImpl implements GetAllTasks {
    final private TaskRepository taskRepository;

    public GetAllTasksImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> execute() {
        return this.taskRepository.findAll();
    }
}
