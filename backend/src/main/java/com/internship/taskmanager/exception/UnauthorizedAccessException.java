package com.internship.taskmanager.exception;

public class UnauthorizedAccessException extends RuntimeException {
  public UnauthorizedAccessException(String message) {
    super(message);
  }

  public UnauthorizedAccessException(String resourceName, Long resourceId) {
    super(String.format("You don't have permission to access %s with id: %d", resourceName, resourceId));
  }
}
