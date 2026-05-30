package com.manage.visitor.model.mapper.admin;

import org.springframework.stereotype.Component;

import com.manage.visitor.model.dto.admin.FeatureDto;
import com.manage.visitor.model.dto.form.FeatureFormDto;
import com.manage.visitor.model.entity.admin.Feature;

import jakarta.persistence.*;

@Component
public class FeatureMapper {

  // GETALL OR GETBYID
  public FeatureDto toDto(Feature data) {
    return new FeatureDto(
        data.getId(), data.getLabel(), data.getCode(), data.getIdKey(), data.getUrl());
  }

  // ADD
  public Feature toEntity(FeatureFormDto dto) {
    return Feature.builder()
        .code(dto.code())
        .label(dto.label())
        .idKey(dto.idKey())
        .url(dto.url())
        .build();
  }
}
