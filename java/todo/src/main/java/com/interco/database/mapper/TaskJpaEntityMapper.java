package com.interco.database.mapper;

import java.util.UUID;

import com.interco.database.entity.TaskJpaEntity;
import com.interco.domain.entity.Task;

public class TaskJpaEntityMapper {

    public static TaskJpaEntity toTaskJpa(Task task) {
        if (task == null) {
            return null;
        }

        return new TaskJpaEntity(
                task.getId().toString(),
                task.getTitle(),
                task.getPosition(),
                task.isCompleted());

    }

    public static Task toDomain(TaskJpaEntity taskJpa) {
        if (taskJpa == null) {
            return null;
        }
        return new Task(
                UUID.fromString(taskJpa.getBusinessId()),
                taskJpa.getTitle(),
                taskJpa.isCompleted(),
                taskJpa.getPosition());

    }

}
