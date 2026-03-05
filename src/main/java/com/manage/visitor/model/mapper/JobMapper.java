package com.manage.visitor.model.mapper;

import com.manage.visitor.model.dto.JobDto;
import com.manage.visitor.model.entity.Job;

public class JobMapper {

  public JobDto toDto(Job data) {
    return new JobDto(data.getId(), data.getLabel(), data.getKeyJobId());
  }

  public Job toEntity(JobDto dto) {
    return new Job(dto.id(), dto.label(), dto.keyJobId());
  }
}
