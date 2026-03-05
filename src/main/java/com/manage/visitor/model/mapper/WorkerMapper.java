package com.manage.visitor.model.mapper;

import com.manage.visitor.model.dto.WorkerDto;
import com.manage.visitor.model.entity.Job;
import com.manage.visitor.model.entity.Worker;

public class WorkerMapper {

  public WorkerDto toDto(Worker data) {
    return new WorkerDto(
        data.getId(),
        data.getName(),
        data.getSurname(),
        data.getBirthDate(),
        data.getIdentifier(),
        data.getJob().getId());
  }

  public Worker toEntity(WorkerDto dto) {
    return new Worker(
        dto.id(),
        dto.name(),
        dto.surname(),
        dto.birthDate(),
        dto.identifier(),
        new Job(dto.jobId(), null, null));
  }
}
