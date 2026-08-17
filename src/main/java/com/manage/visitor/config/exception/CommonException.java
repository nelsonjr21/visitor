package com.manage.visitor.config.exception;

import com.manage.visitor.config.http.ErrorCode;

public class CommonException extends RuntimeException {
  private final ErrorCode errorCode;

  public CommonException(ErrorCode code, String message) {
    super(message);
    this.errorCode = code;
  }

  public ErrorCode getCode() {
    return errorCode;
  }
}
