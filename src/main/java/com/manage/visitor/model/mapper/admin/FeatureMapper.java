package com.manage.visitor.model.mapper.admin;

import com.manage.visitor.model.dto.admin.FeatureDto;
import com.manage.visitor.model.entity.admin.Feature;

public class FeatureMapper {

  public FeatureDto toDto(Feature data) {
    return new FeatureDto(
        data.getId(),
        data.getLabel(),
        data.getCode(),
        data.getIsKeyFeature(),
        data.getUrlKeyFeature());
  }

  public Feature toEntity(FeatureDto dto) {
    return new Feature(dto.id(), dto.label(), dto.code(), dto.isKeyFeature(), dto.urlKeyFeature());
  }
}
