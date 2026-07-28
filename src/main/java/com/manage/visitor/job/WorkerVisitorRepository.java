package com.manage.visitor.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage.visitor.woker_visitor.WorkerVisitor;

@Repository
public interface WorkerVisitorRepository extends JpaRepository<WorkerVisitor, Integer> {}
