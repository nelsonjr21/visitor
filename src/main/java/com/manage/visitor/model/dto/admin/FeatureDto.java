package com.manage.visitor.model.dto.admin;

import com.manage.visitor.model.entity.admin.Feature;

public record FeatureDto(Integer id, String label, String code, Integer idKey, String url) {
  public static Feature toEntity(FeatureDto dto) {
    return new Feature(dto.id(), dto.label(), dto.code(), dto.idKey(), dto.url());
  }

  public static FeatureDto entityToDTO(Feature data) {
    return new FeatureDto(
        data.getId(), data.getLabel(), data.getCode(), data.getIdKey(), data.getUrl());
  }
}
