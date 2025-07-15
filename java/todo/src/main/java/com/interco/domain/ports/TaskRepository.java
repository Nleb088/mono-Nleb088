package com.interco.domain.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.interco.domain.entity.Task;

public interface TaskRepository {

    Task save(Task task);

    List<Task> saveMultiple(List<Task> task);

    Optional<Task> findById(UUID id);

    List<Task> findAll();

    void deleteById(UUID id);

    void deleteAll();

    long count();
}