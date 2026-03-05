package com.manage.visitor.model.dto.admin;

public record FeatureRoleDto(
    Integer id, String urlFeature, String stateFeature, Integer roleId, Integer featureId) {}
