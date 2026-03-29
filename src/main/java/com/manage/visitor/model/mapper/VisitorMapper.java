package com.manage.visitor.model.mapper;

import org.springframework.stereotype.Component;

import com.manage.visitor.model.dto.VisitorDto;
import com.manage.visitor.model.entity.Visitor;

@Component
public class VisitorMapper {

  public VisitorDto toDto(Visitor data) {
    return new VisitorDto(data.getId(), data.getName(), data.getSurname());
  }

  public Visitor toEntity(VisitorDto dto) {
    return new Visitor(dto.id(), dto.name(), dto.surname(), null);
  }
}
