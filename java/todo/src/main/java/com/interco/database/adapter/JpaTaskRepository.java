package com.interco.database.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.interco.domain.entity.Task;
import com.interco.domain.ports.TaskRepository;

@Repository
public class JpaTaskRepository implements TaskRepository {

    @Override
    public Task save(Task task) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Task> findById(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Task> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Task> saveMultiple(List<Task> task) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Integer count() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
