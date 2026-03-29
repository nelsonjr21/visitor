package com.manage.visitor.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage.visitor.model.entity.admin.RoleFeature;

@Repository
public interface RoleFeatureRepository extends JpaRepository<RoleFeature, Integer> {
  List<RoleFeature> findByFeature_IdKey(Integer featureIdKey);

  boolean existsByFeature_Id(Integer featureId);

  List<RoleFeature> findByRole_Id(Integer roleId);
}
