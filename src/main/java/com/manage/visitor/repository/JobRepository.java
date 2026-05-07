package com.manage.visitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage.visitor.model.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    Job findJobById(Integer id);
}
