package com.interco.domain.usecases;

import java.util.Optional;
import java.util.UUID;

import com.interco.domain.entity.EditTaskInput;
import com.interco.domain.entity.Task;
import com.interco.domain.ports.TaskRepository;
import com.interco.domain.ports.usecases.EditTask;
import com.interco.rest.entity.TaskUpdateDto;

public class EditTaskImpl implements EditTask {

    final private TaskRepository taskRepository;

    public EditTaskImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task execute(UUID taskId, EditTaskInput input) {

        Optional<Task> existingTaskOptional = taskRepository.findById(taskId);

        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();
            if (input.title() != null) {
                existingTask.setTitle(input.title());
            }
            if (input.completed() != null) {
                existingTask.setCompleted(input.completed());
            }
            return taskRepository.save(existingTask);
        } else {
            return null;
        }
    }

}
