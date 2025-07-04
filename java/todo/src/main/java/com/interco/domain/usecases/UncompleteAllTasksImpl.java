package com.interco.domain.usecases;

import java.util.List;

import com.interco.domain.entity.Task;
import com.interco.domain.ports.TaskRepository;
import com.interco.domain.ports.usecases.UncompleteAllTasks;

public class UncompleteAllTasksImpl implements UncompleteAllTasks {
    final private TaskRepository taskRepository;

    public UncompleteAllTasksImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> execute() {
        List<Task> tasks = this.taskRepository.findAll();

        for (Task t : tasks) {
            t.uncomplete();
        }

        return taskRepository.saveMultiple(tasks);
    }
}
