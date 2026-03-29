package com.manage.visitor.model.dto.admin;

import com.manage.visitor.model.entity.admin.Role;

public record RoleDto(Integer id, String label, String code) {
  public static Role toEntity(RoleDto dto) {
    return new Role(dto.id(), dto.label(), dto.code());
  }

  public static RoleDto entityToDTO(Role data) {
    return new RoleDto(data.getId(), data.getLabel(), data.getCode());
  }
}
