package com.interco.domain.usecases;

import com.interco.domain.entity.Task;
import com.interco.domain.ports.TaskRepository;
import com.interco.domain.ports.usecases.CreateTask;

public class CreateTaskImpl implements CreateTask {

    final private TaskRepository taskRepository;

    public CreateTaskImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task execute(String title) {
        Task task = new Task(title, this.taskRepository.count());
        return this.taskRepository.save(task);
    }
}
