package com.manage.visitor.config.exception;

import com.manage.visitor.config.http.ErrorCode;

public class BadRequestException extends CommonException {
  public BadRequestException(String message) {
    super(ErrorCode.BAD_REQUEST, message);
  }
}
