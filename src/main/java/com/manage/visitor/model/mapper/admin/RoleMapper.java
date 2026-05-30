package com.manage.visitor.model.mapper.admin;

import org.springframework.stereotype.Component;

import com.manage.visitor.model.dto.admin.RoleDto;
import com.manage.visitor.model.dto.form.RoleFormDto;
import com.manage.visitor.model.entity.admin.Role;

@Component
public class RoleMapper {

  // GETALL OR GETBYID
  public RoleDto toDto(Role data) {
    return new RoleDto(data.getId(), data.getLabel(), data.getCode());
  }

  // ADD
  public Role toEntity(RoleFormDto dto) {
    return Role.builder().label(dto.label()).code(dto.code()).build();
  }
}
