package com.manage.visitor.admin.feature;

import org.springframework.stereotype.Component;

@Component
public class FeatureMapper {

    // GETALL OR GETBYID
    public FeatureDto toDto(Feature data) {
        return new FeatureDto(
                data.getId(),
                data.getLabel(),
                data.getCode(),
                data.getKeyId(),
                data.getUrl());
    }

    // ADD
    public Feature toEntity(FeatureFormDto dto) {
        return Feature.builder()
                .code(dto.code())
                .label(dto.label())
                .keyId(dto.idKey())
                .url(dto.url())
                .build();
    }

}