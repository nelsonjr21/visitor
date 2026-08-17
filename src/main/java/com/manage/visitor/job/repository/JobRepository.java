package com.manage.visitor.job.repository;

import com.manage.visitor.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    Job findJobById(Integer id);
}
