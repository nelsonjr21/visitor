package com.manage.visitor.admin.role.repository;

import com.manage.visitor.admin.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findByRoleWorkersWorkerId(Integer workerId);
}
