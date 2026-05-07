package com.manage.visitor.repository.admin;

import java.util.List;

import com.manage.visitor.model.entity.admin.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage.visitor.model.entity.admin.RoleWorker;

@Repository
public interface RoleWorkerRepository extends JpaRepository<RoleWorker, Integer> {
  List<RoleWorker> findByWorker_Id(Integer workerId);

  List<RoleWorker> findByWorker_Identifier(String workerIdentifier);

  boolean findByRole_IdAndWorker_Id(Integer roleId, Integer workerId);
}
