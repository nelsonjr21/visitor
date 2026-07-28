package com.manage.visitor.job.mapper;

import com.manage.visitor.job.dto.JobDto;
import com.manage.visitor.job.dto.JobFormDto;
import com.manage.visitor.job.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

  // GETALL OR GETBYID
  public JobDto toDto(Job data) {
    return new JobDto(
            data.getId(),
            data.getLabel(),
            data.getKeyJobId());
  }

  // ADD
  public Job toEntity(JobFormDto dto) {
    return Job.builder()
            .label(dto.label())
            .keyJobId(dto.keyJobId())
            .build();
  }

}
