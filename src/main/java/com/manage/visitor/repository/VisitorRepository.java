package com.manage.visitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage.visitor.model.entity.Visitor;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Integer> {}
