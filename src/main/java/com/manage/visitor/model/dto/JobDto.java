package com.manage.visitor.model.dto;

import com.manage.visitor.model.entity.Job;

public record JobDto(Integer id, String label, Integer keyJobId) {
  public JobDto(Job data) {
    this(data.getId(), data.getLabel(), data.getKeyJobId());
  }
}
