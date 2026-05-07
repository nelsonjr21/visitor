package com.manage.visitor.model.mapper;

import com.manage.visitor.model.dto.JobDto;
import com.manage.visitor.model.dto.VisitorDto;
import com.manage.visitor.model.dto.form.JobFormDto;
import com.manage.visitor.model.entity.Job;
import com.manage.visitor.model.entity.Visitor;
import com.manage.visitor.model.entity.Worker;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Set;

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
