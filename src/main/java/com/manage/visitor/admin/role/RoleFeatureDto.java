package com.manage.visitor.admin.role;

import com.manage.visitor.admin.feature.FeatureMinDto;

import java.util.List;

public record RoleFeatureDto(Integer roleId, List<FeatureMinDto> features) {}
