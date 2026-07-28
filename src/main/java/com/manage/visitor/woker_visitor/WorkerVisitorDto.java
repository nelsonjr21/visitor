package com.manage.visitor.woker_visitor;

import java.time.LocalDateTime;

public record WorkerVisitorDto(
    Integer id, LocalDateTime visitDate, Integer workerId, Integer visitorId) {}
