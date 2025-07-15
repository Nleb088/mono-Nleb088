package com.interco.domain.usecases;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.interco.domain.entity.Task;
import com.interco.domain.ports.TaskRepository;
import com.interco.domain.ports.usecases.ReorderTask;

public class ReorderTaskImpl implements ReorderTask {

    final private TaskRepository taskRepository;

    public ReorderTaskImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> execute(UUID id, Long newPosition) {
        List<Task> tasks = this.taskRepository.findAll();
        Optional<Task> taskToMoveOptional = tasks.stream().filter(task -> task.getId().equals(id))
                .findFirst();

        if (taskToMoveOptional.isEmpty()) {
            return tasks;
        }

        newPosition = Math.max(0, Math.min(newPosition, tasks.size()));
        Task taskToMove = taskToMoveOptional.get();
        long oldPosition = taskToMove.getPosition();

        for (Task t : tasks) {
            if (oldPosition < newPosition) {
                if (t.getPosition() > oldPosition && t.getPosition() <= newPosition) {
                    t.setPosition(t.getPosition() - 1);
                }
            } else {
                if (t.getPosition() >= newPosition && t.getPosition() < oldPosition) {
                    t.setPosition(t.getPosition() + 1);
                }
            }
        }

        return taskRepository.saveMultiple(tasks);
    }
}
