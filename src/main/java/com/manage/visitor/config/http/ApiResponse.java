package com.manage.visitor.config.http;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ApiResponse<T>(
    String code, String message, @JsonInclude(JsonInclude.Include.NON_NULL) T data) {
  public static <T> ApiResponse<T> success(T data) {
    if (data != null) {
      return new ApiResponse<>("200", "Success", data);
    } else return new ApiResponse<>("200", "Success", null);
  }
}
