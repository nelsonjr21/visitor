package com.manage.visitor.model.dto;

import com.manage.visitor.model.entity.Job;

public record JobDto(Integer id, String label, Integer keyJobId) {
  public static JobDto convertToDto(Job data) {
    return new JobDto(data.getId(), data.getLabel(), data.getKeyJobId());
  }

  public static Job toEntity(JobDto dto) {
    return new Job(dto.id(), dto.label(), dto.keyJobId(), null);
  }
}
