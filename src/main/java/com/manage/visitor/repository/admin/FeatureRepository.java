package com.manage.visitor.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage.visitor.model.entity.admin.Feature;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {
  List<Feature> findByIdKeyIsNotNull();

  List<Feature> findByIdKey(Integer idKey);

  boolean existsByIdKey(Integer idKey);
}
