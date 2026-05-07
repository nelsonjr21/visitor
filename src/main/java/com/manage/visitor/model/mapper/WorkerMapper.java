package com.manage.visitor.model.mapper;

import com.manage.visitor.model.dto.JobDto;
import com.manage.visitor.model.dto.WorkerDto;
import com.manage.visitor.model.dto.admin.RoleDto;
import com.manage.visitor.model.dto.form.WorkerFormDto;
import com.manage.visitor.model.entity.Job;
import com.manage.visitor.model.entity.Worker;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkerMapper {

  public WorkerDto toDtoWithToken(Worker data, List<RoleDto> roleDtoList, JobDto jobDto, String token) {
    return new WorkerDto(
            data.getId(),
            data.getName(),
            data.getSurname(),
            data.getBirthDate(),
            data.getIdentifier(),
            roleDtoList,
            jobDto.label(),
            token);
  }

  public WorkerDto toDtoWithJobDtoAndRoleDtoList(Worker data, Job job, List<RoleDto> roleDtoList) {
    return new WorkerDto(
            data.getId(),
            data.getName(),
            data.getSurname(),
            data.getBirthDate(),
            data.getIdentifier(),
            roleDtoList,
            job.getLabel(),
            null);
  }

  public Worker toEntity(WorkerFormDto dto) {
    return Worker.builder()
            .name(dto.name())
            .surname(dto.surname())
            .birthDate(dto.birthDate())
            .identifier(dto.identifier())
            .pw(dto.password())
            .job(Job.builder().id(dto.jobId()).build())
            .build();
  }
}
