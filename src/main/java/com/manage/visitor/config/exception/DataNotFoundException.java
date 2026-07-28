package com.manage.visitor.config.exception;

import com.manage.visitor.config.http.ErrorCode;

public class DataNotFoundException extends CommonException {
  public DataNotFoundException(String message) {
    super(ErrorCode.NOT_FOUND, message);
  }
}
