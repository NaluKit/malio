package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractBlacklistConstraint
    extends AbstractConstraint<String> {

  private String message;

  private List<String> blacklist;

  public AbstractBlacklistConstraint(String packageName,
                                     String simpleName,
                                     String fieldName,
                                     String[] blacklist) {
    super(packageName,
          simpleName,
          fieldName);
    this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
    this.blacklist = Arrays.asList(blacklist);
  }

  public void check(String value) throws MalioValidationException {
    if (value != null && blacklist.contains(value)) {
      throw new MalioValidationException(this.message);
    }
  }

  public void isValid(String value, ValidationResult validationResult) {
    if (value != null &&  blacklist.contains(value)) {
      validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
    }
  }
}
