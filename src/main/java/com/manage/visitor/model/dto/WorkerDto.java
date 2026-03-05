package com.manage.visitor.model.dto;

import java.time.LocalDate;

public record WorkerDto(
    Integer id,
    String name,
    String surname,
    LocalDate birthDate,
    String identifier,
    Integer jobId) {}
