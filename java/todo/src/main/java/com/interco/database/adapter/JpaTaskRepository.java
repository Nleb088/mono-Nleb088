package com.interco.database.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.interco.database.entity.TaskJpaEntity;
import com.interco.database.mapper.TaskJpaEntityMapper;
import com.interco.domain.entity.Task;
import com.interco.domain.ports.TaskRepository;

@Repository
public class JpaTaskRepository implements TaskRepository {

    private final SpringDataTaskRepository springDataTaskRepository;

    public JpaTaskRepository(SpringDataTaskRepository springDataTaskRepository) {
        this.springDataTaskRepository = springDataTaskRepository;
    }

    @Override
    public Task save(Task task) {
        TaskJpaEntity taskJpaEntity = TaskJpaEntityMapper.toTaskJpa(task);

        return TaskJpaEntityMapper.toDomain(springDataTaskRepository.save(taskJpaEntity));
    }

    @Override
    public Optional<Task> findById(String id) {
        Optional<TaskJpaEntity> entityOptional = springDataTaskRepository.findByBusinessId(id);

        return entityOptional.map(TaskJpaEntityMapper::toDomain);
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new java.util.ArrayList<>();
        springDataTaskRepository.findAll().forEach(dbEntity -> tasks.add(TaskJpaEntityMapper.toDomain(dbEntity)));
        return tasks;
    }

    @Override
    public void deleteById(String id) {
        springDataTaskRepository.deleteByBusinessId(id);
    }

    @Override
    public void deleteAll() {
        springDataTaskRepository.deleteAll();
    }

    @Override
    public List<Task> saveMultiple(List<Task> tasks) {
        List<TaskJpaEntity> savedTasks = new java.util.ArrayList<>();

        List<TaskJpaEntity> taskJpaEntities = tasks.stream().map(TaskJpaEntityMapper::toTaskJpa)
                .collect(Collectors.toList());

        springDataTaskRepository.saveAll(taskJpaEntities).forEach(savedTasks::add);
        return savedTasks.stream().map(TaskJpaEntityMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return springDataTaskRepository.count();
    }
}
