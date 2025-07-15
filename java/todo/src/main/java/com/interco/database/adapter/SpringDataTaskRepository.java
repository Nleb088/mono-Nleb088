package com.interco.database.adapter;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.interco.database.entity.TaskJpaEntity;

public interface SpringDataTaskRepository extends CrudRepository<TaskJpaEntity, Long> {

    Optional<TaskJpaEntity> findByBusinessId(String businessId);

    void deleteByBusinessId(String businessId);
}
