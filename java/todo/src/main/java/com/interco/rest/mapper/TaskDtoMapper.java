package com.interco.rest.mapper;


import com.interco.domain.entity.Task;
import com.interco.rest.entity.TaskDto;

public class TaskDtoMapper {

    public TaskDto mapTaskToTaskDto(Task task) {
        return new TaskDto();
    }

    public Task mapTaskDtoToTask(TaskDto taskPersistance) {
        return new Task("ds", 0);
    }
}