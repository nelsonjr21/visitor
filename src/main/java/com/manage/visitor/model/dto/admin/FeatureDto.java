package com.manage.visitor.model.dto.admin;

import com.manage.visitor.model.entity.admin.Feature;

public record FeatureDto(Integer id, String label, String code, Integer idKey, String url) {
  public FeatureDto(Feature data) {
    this(data.getId(), data.getLabel(), data.getCode(), data.getIdKey(), data.getUrl());
  }
}
