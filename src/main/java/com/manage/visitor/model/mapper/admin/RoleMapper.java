package com.manage.visitor.model.mapper.admin;

import com.manage.visitor.model.dto.admin.RoleDto;
import com.manage.visitor.model.entity.admin.Role;

public class RoleMapper {

  public RoleDto toDto(Role data) {
    return new RoleDto(data.getId(), data.getLabel(), data.getCode());
  }

  public Role toEntity(RoleDto dto) {
    return new Role(dto.id(), dto.label(), dto.code());
  }
}
