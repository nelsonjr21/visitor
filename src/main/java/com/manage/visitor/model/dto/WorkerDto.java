package com.manage.visitor.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.manage.visitor.model.dto.admin.RoleDto;
import com.manage.visitor.model.entity.Job;
import com.manage.visitor.model.entity.Worker;

public record WorkerDto(
    Integer id,
    String name,
    String surname,
    LocalDate birthDate,
    String identifier,
    @JsonInclude(JsonInclude.Include.NON_NULL) String password,
    List<RoleDto> roleList,
    JobDto job,
    @JsonInclude(JsonInclude.Include.NON_NULL) String token) {
  public static WorkerDto forLogin(
      Worker data, List<RoleDto> roleDtoList, JobDto jobDto, String token) {
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

  public static WorkerDto result(Worker data, JobDto jobDto, List<RoleDto> roleDtoList) {
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

  public static Worker toEntity(WorkerDto dto, Job job) {
    return new Worker(
        dto.id(),
        dto.name(),
        dto.surname(),
        dto.birthDate(),
        dto.identifier(),
        dto.password(),
        job);
  }
}
