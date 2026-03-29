package com.manage.visitor.model.dto.admin;

import java.util.List;

public record RoleFeatureDto(Integer roleId, List<FeatureMinDto> features) {}
