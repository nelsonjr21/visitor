package com.manage.visitor.model.mapper.admin;

import com.manage.visitor.model.dto.JobDto;
import com.manage.visitor.model.dto.admin.FeatureDto;
import com.manage.visitor.model.entity.Job;
import com.manage.visitor.model.entity.admin.Feature;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class FeatureMapper {

  public FeatureDto toDto(Feature data) {
    return new FeatureDto(
            data.getId(),
            data.getLabel(),
            data.getCode(),
            data.getIdKey(),
            data.getUrl());
  }

  public Feature toEntity(FeatureDto dto) {
    return new Feature(
            dto.id(),
            dto.label(),
            dto.code(),
            dto.idKey(),
            dto.url());
  }

}
