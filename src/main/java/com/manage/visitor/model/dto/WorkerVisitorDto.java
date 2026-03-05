package com.manage.visitor.model.dto;

import java.time.LocalDateTime;

public record WorkerVisitorDto(
    Integer id, LocalDateTime visitDate, Integer workerId, Integer visitorId) {}
