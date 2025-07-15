package com.interco.domain.usecases;

import java.util.List;

import com.interco.domain.entity.Task;
import com.interco.domain.ports.TaskRepository;
import com.interco.domain.ports.usecases.DeleteAllCompletedTasks;

public class DeleteAllCompletedTasksImpl implements DeleteAllCompletedTasks {
    final private TaskRepository taskRepository;

    public DeleteAllCompletedTasksImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void execute() {
        List<Task> tasks = this.taskRepository.findAll();

        for (Task t : tasks) {
            if (t.isCompleted())
                taskRepository.deleteById(t.getId());
        }
    }

}
