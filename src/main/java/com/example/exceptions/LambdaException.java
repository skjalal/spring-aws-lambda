package com.example.exceptions;

import java.io.Serial;

public class LambdaException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public LambdaException(String message, Throwable cause) {
    super(message, cause);
  }
}
