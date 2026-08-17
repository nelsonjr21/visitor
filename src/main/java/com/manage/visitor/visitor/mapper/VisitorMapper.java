package com.manage.visitor.visitor.mapper;

import com.manage.visitor.visitor.dto.VisitorDto;
import com.manage.visitor.visitor.dto.VisitorFormDto;
import com.manage.visitor.visitor.entity.Visitor;
import org.springframework.stereotype.Component;

@Component
public class VisitorMapper {

// GETALL OR GETBYID
  public VisitorDto toDto(Visitor data) {
    return new VisitorDto(
            data.getId(),
            data.getName(),
            data.getSurname());
  }

  // ADD
  public Visitor toEntity(VisitorFormDto dto) {
    return Visitor.builder()
            .name(dto.name())
            .surname(dto.surname())
            .build();
  }

}
