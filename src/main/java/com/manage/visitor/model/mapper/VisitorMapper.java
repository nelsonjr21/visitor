package com.manage.visitor.model.mapper;

import com.manage.visitor.model.dto.VisitorDto;
import com.manage.visitor.model.entity.Visitor;

public class VisitorMapper {

  public VisitorDto toDto(Visitor data) {
    return new VisitorDto(data.getId(), data.getName(), data.getSurname());
  }

  public Visitor toEntity(VisitorDto dto) {
    return new Visitor(dto.id(), dto.name(), dto.surname());
  }
}
