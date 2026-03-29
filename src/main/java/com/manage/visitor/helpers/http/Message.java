package com.manage.visitor.helpers.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Message {
  GLOBAL_ALERT("An error has occurred. Please wait and try again."),
  DATA_NOT_FOUND("Data not found"),
  ID_NOT_REQUIRED("Id not required"),
  ID_REQUIRED("Id required"),
  UNAUTHORIZED("Username or password incorrect"),
  TOKEN_REQUIRED("Token required");

  public final String text;
}
