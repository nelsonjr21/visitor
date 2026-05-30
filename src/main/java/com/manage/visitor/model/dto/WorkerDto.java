package com.manage.visitor.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.manage.visitor.model.dto.admin.RoleDto;

public record WorkerDto(
    Integer id,
    String name,
    String surname,
    LocalDate birthDate,
    String identifier,
    List<RoleDto> roleList,
    String job,
    @JsonInclude(JsonInclude.Include.NON_NULL) String token) {}
