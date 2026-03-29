package com.manage.visitor.helpers.exception;

import com.manage.visitor.helpers.http.ErrorCode;

public class BadRequestException extends CommonException {
  public BadRequestException(String message) {
    super(ErrorCode.BAD_REQUEST, message);
  }
}
