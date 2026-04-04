package com.manage.visitor.model.dto.admin;

import com.manage.visitor.model.entity.admin.Role;

public record RoleDto(Integer id, String label, String code) {
  public RoleDto(Role data) {
    this(data.getId(), data.getLabel(), data.getCode());
  }
}
