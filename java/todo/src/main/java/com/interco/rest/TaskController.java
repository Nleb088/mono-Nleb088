package com.interco.rest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interco.domain.entity.Task;
import com.interco.domain.ports.usecases.CompleteAllTasks;
import com.interco.domain.ports.usecases.CreateTask;
import com.interco.domain.ports.usecases.DeleteAllCompletedTasks;
import com.interco.domain.ports.usecases.DeleteTask;
import com.interco.domain.ports.usecases.EditTask;
import com.interco.domain.ports.usecases.GetAllTasks;
import com.interco.domain.ports.usecases.ReorderTask;
import com.interco.domain.ports.usecases.UncompleteAllTasks;
import com.interco.rest.entity.TaskDto;
import com.interco.rest.entity.TaskUpdateDto;
import com.interco.rest.mapper.TaskDtoMapper;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final CompleteAllTasks completeAllTasksUseCase;
    private final CreateTask createTaskUseCase;
    private final DeleteAllCompletedTasks deleteAllCompletedTasksUseCase;
    private final DeleteTask deleteTaskUseCase;
    private final EditTask editTaskUseCase;
    private final GetAllTasks getAllTasksUseCase;
    private final ReorderTask reorderTaskUseCase;
    private final UncompleteAllTasks uncompleteAllTasksUseCase;

    public TaskController(
            CompleteAllTasks completeAllTasksUseCase,
            CreateTask createTaskUseCase,
            DeleteAllCompletedTasks deleteAllCompletedTasksUseCase,
            DeleteTask deleteTaskUseCase,
            EditTask editTaskUseCase,
            GetAllTasks getAllTasksUseCase,
            ReorderTask reorderTaskUseCase,
            UncompleteAllTasks uncompleteAllTasksUseCase) {
        this.completeAllTasksUseCase = completeAllTasksUseCase;
        this.createTaskUseCase = createTaskUseCase;
        this.deleteAllCompletedTasksUseCase = deleteAllCompletedTasksUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.editTaskUseCase = editTaskUseCase;
        this.getAllTasksUseCase = getAllTasksUseCase;
        this.reorderTaskUseCase = reorderTaskUseCase;
        this.uncompleteAllTasksUseCase = uncompleteAllTasksUseCase;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<Task> tasks = getAllTasksUseCase.execute();
        List<TaskDto> taskDtos = tasks.stream()
                .map(TaskDtoMapper::ToTaskDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(taskDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody String title) {

        Task createdTask = createTaskUseCase.execute(title);
        return new ResponseEntity<>(TaskDtoMapper.ToTaskDto(createdTask), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDto> editTaskTitle(@PathVariable String id, @RequestBody TaskUpdateDto taskUpdateDto) {

        Task updatedTask = editTaskUseCase.execute(UUID.fromString(id), TaskDtoMapper.ToEditTaskInput(taskUpdateDto));

        if (updatedTask != null) {
            return new ResponseEntity<>(TaskDtoMapper.ToTaskDto(updatedTask), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        deleteTaskUseCase.execute(UUID.fromString(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/complete-all")
    public ResponseEntity<Void> completeAllTasks() {
        completeAllTasksUseCase.execute();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/uncomplete-all")
    public ResponseEntity<Void> uncompleteAllTasks() {
        uncompleteAllTasksUseCase.execute();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/completed")
    public ResponseEntity<Void> deleteAllCompletedTasks() {
        deleteAllCompletedTasksUseCase.execute();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/reorder")
    public ResponseEntity<List<TaskDto>> reorderTask(@PathVariable String id, @RequestBody Long position) {
        List<Task> tasks = reorderTaskUseCase.execute(UUID.fromString(id), position);
        List<TaskDto> taskDtos = tasks.stream()
                .map(TaskDtoMapper::ToTaskDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(taskDtos, HttpStatus.OK);
    }

}
