package com.manage.visitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage.visitor.model.entity.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {
  Worker findByIdentifier(String identifier);
}
