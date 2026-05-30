package com.manage.visitor.model.dto.form;

import java.time.LocalDate;
import java.util.List;

public record WorkerFormDto(
    String name,
    String surname,
    LocalDate birthDate,
    String identifier,
    String password,
    List<Integer> roleList,
    Integer jobId) {}
