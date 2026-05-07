package com.manage.visitor.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage.visitor.model.entity.admin.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findByRoleWorkersWorkerId(Integer workerId);
}
