package com.github.nalukit.malio.shared.model;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

  private List<ErrorMessage> messages;

  public ValidationResult() {
    this.messages = new ArrayList<>();
  }

  public List<ErrorMessage> getMessages() {
    return messages;
  }

  public void invlidate(String message, String classnmae, String field) {
    this.messages.add(new ErrorMessage(message, classnmae, field));
  }

  public boolean isValid() {
    return this.messages.size() == 0;
  }
}
