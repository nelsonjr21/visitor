package com.manage.visitor.config.exception;

import com.manage.visitor.config.http.ErrorCode;

public class AppException extends CommonException {
  public AppException(String message) {
    super(ErrorCode.INTERNAL_ERROR, message);
  }
}
