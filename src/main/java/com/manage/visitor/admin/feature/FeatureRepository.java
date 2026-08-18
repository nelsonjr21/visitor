package com.manage.visitor.admin.feature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {
    List<Feature> findByIdKeyIsNotNull();

    List<Feature> findByIdKey(Integer idKey);

    boolean existsByIdKey(Integer idKey);
}
