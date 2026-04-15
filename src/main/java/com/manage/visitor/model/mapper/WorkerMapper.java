package com.manage.visitor.model.mapper;

import com.manage.visitor.model.dto.JobDto;
import com.manage.visitor.model.dto.WorkerDto;
import com.manage.visitor.model.dto.admin.RoleDto;
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
            null,
            roleDtoList,
            jobDto,
            token);
  }

  public WorkerDto toDtoWithJobDtoAndRoleDtoList(Worker data, JobDto jobDto, List<RoleDto> roleDtoList) {
    return new WorkerDto(
            data.getId(),
            data.getName(),
            data.getSurname(),
            data.getBirthDate(),
            data.getIdentifier(),
            null,
            roleDtoList,
            jobDto,
            null);
  }

  public Worker toEntity(WorkerDto dto) {
    return new Worker(dto.id(), dto.name(), dto.surname(), dto.birthDate(), dto.identifier(), dto.password(), new Job(dto.job().id()));
  }
}
