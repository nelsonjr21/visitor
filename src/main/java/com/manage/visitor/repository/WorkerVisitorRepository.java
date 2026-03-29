package com.manage.visitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage.visitor.model.entity.WorkerVisitor;

@Repository
public interface WorkerVisitorRepository extends JpaRepository<WorkerVisitor, Integer> {}
