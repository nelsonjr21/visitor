package com.manage.visitor.worker.mapper;

import com.manage.visitor.job.dto.JobDto;
import com.manage.visitor.admin.role.dto.RoleDto;
import com.manage.visitor.job.entity.Job;
import com.manage.visitor.worker.dto.WorkerDto;
import com.manage.visitor.worker.dto.WorkerFormDto;
import com.manage.visitor.worker.entity.Worker;
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
