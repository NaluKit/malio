package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;

import java.util.Objects;
import java.util.regex.Pattern;

public abstract class AbstractEmailConstraint
    extends AbstractConstraint<String> {

  private String message;

  //https://owasp.org/www-community/OWASP_Validation_Regex_Repository
  private final static Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
  public AbstractEmailConstraint(String packageName,
                                 String simpleName,
                                 String fieldName) {
    super(packageName,
          simpleName,
          fieldName);
    this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
  }

  public void check(String value) throws MalioValidationException {
    if (Objects.nonNull(value) && !pattern.matcher(value).matches()) {
      throw new MalioValidationException(this.message);
    }
  }

  public void  isValid(String value, ValidationResult validationResult) {
    if (Objects.nonNull(value)  && !pattern.matcher(value).matches()) {
      validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
    }
  }
}
