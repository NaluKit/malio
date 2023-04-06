package com.github.nalukit.malio.shared;

import com.github.nalukit.malio.shared.model.ValidationResult;

public class MalioValidationException
    extends Exception {

  public MalioValidationException() {
  }

  public MalioValidationException(String message) {
    super(message);
  }
}
