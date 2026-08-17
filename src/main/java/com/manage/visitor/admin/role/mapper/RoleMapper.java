package com.manage.visitor.admin.role.mapper;

import com.manage.visitor.admin.role.dto.RoleDto;
import com.manage.visitor.admin.role.dto.RoleFormDto;
import com.manage.visitor.admin.role.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

  // GETALL OR GETBYID
  public RoleDto toDto(Role data) {
    return new RoleDto(
            data.getId(),
            data.getLabel(),
            data.getCode());
  }

  // ADD
  public Role toEntity(RoleFormDto dto) {
    return Role.builder()
              .label(dto.label())
              .code(dto.code())
              .build();
  }

}
