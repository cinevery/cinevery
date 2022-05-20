package com.cinevery.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {

  public UserNotActivatedException(String message) {
    super(message);
  }

  public UserNotActivatedException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
