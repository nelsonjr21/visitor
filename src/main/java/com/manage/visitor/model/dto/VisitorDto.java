package com.manage.visitor.model.dto;

import com.manage.visitor.model.entity.Visitor;

public record VisitorDto(Integer id, String name, String surname) {
  public VisitorDto(Visitor data) {
    this(data.getId(), data.getName(), data.getSurname());
  }
}
