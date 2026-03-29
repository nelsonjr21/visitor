package com.manage.visitor.helpers.exception;

import com.manage.visitor.helpers.http.ErrorCode;

public class DataNotFoundException extends CommonException {
  public DataNotFoundException(String message) {
    super(ErrorCode.NOT_FOUND, message);
  }
}
