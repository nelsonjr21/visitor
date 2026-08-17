package com.manage.visitor.worker.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.manage.visitor.admin.role.dto.RoleDto;

public record WorkerDto(
    Integer id,
    String name,
    String surname,
    LocalDate birthDate,
    String identifier,
    List<RoleDto> roleList,
    String job,
    @JsonInclude(JsonInclude.Include.NON_NULL) String token) {
}
