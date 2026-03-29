package com.manage.visitor.helpers.exception;

import com.manage.visitor.helpers.http.ErrorCode;

public class AppException extends CommonException {
  public AppException(String message) {
    super(ErrorCode.INTERNAL_ERROR, message);
  }
}
