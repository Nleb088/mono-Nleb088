package com.interco.rest.mapper;

import com.interco.domain.entity.EditTaskInput;
import com.interco.domain.entity.Task;
import com.interco.rest.entity.TaskDto;
import com.interco.rest.entity.TaskUpdateDto;

public class TaskDtoMapper {

    public static TaskDto ToTaskDto(Task task) {
        return new TaskDto(task.getId().toString(), task.getTitle(), task.getPosition(), task.isCompleted());
    }

    public static EditTaskInput ToEditTaskInput(TaskUpdateDto taskUpdateDto) {
        return new EditTaskInput(
                taskUpdateDto.title().orElse(null),
                taskUpdateDto.completed().orElse(null));
    }

}