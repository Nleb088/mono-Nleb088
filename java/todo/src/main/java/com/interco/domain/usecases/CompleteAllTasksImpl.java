package com.interco.domain.usecases;

import java.util.List;

import com.interco.domain.entity.Task;
import com.interco.domain.ports.TaskRepository;
import com.interco.domain.ports.usecases.CompleteAllTasks;

public class CompleteAllTasksImpl implements CompleteAllTasks {
    final private TaskRepository taskRepository;

    public CompleteAllTasksImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> execute() {
        List<Task> tasks = this.taskRepository.findAll();

        for (Task t : tasks) {
            t.complete();
        }

        return taskRepository.saveMultiple(tasks);
    }

}
