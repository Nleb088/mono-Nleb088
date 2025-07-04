package com.interco.domain.ports;

import java.util.List;
import java.util.Optional;

import com.interco.domain.entity.Task;

public interface TaskRepository {

    Task save(Task task);

    List<Task> saveMultiple(List<Task> task);

    Optional<Task> findById(String id);

    List<Task> findAll();

    void deleteById(String id);

    void deleteAll();

    Integer count();
}