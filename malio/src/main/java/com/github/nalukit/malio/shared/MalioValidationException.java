package com.github.nalukit.malio.shared;

// TODO move class to util package
@SuppressWarnings("unchecked")
public class MalioValidationException
    extends Exception {

  public MalioValidationException() {
  }

  public MalioValidationException(String message) {
    super(message);
  }
}
