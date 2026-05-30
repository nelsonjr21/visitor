package com.manage.visitor.model.mapper;

import org.springframework.stereotype.Component;

import com.manage.visitor.model.dto.VisitorDto;
import com.manage.visitor.model.dto.form.VisitorFormDto;
import com.manage.visitor.model.entity.Visitor;

@Component
public class VisitorMapper {

  // GETALL OR GETBYID
  public VisitorDto toDto(Visitor data) {
    return new VisitorDto(data.getId(), data.getName(), data.getSurname());
  }

  // ADD
  public Visitor toEntity(VisitorFormDto dto) {
    return Visitor.builder().name(dto.name()).surname(dto.surname()).build();
  }
}
