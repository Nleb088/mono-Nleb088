package com.interco.domain.usecases;

import com.interco.domain.entity.Task;
import com.interco.domain.ports.TaskRepository;
import com.interco.domain.ports.usecases.EditTask;

public class EditTaskImpl implements EditTask {

    final private TaskRepository taskRepository;

    public EditTaskImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task execute(Task task) {
        return this.taskRepository.save(task);
    }

}
