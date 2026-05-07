package com.manage.visitor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JobDto(Integer id, String label, Integer keyJobId) {
}
