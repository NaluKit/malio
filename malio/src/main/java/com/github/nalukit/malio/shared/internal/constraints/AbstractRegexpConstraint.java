package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractRegexpConstraint
        extends AbstractConstraint<String> {

    private String message;

    private Pattern pattern;

    public AbstractRegexpConstraint(String packageName,
                                    String simpleName,
                                    String fieldName,
                                    String regexp) {
        super(packageName,
                simpleName,
                fieldName);
        this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
        this.pattern = Pattern.compile(regexp);
    }

    public void check(String value) throws MalioValidationException {
        if (value != null) {
            Matcher matcher = this.pattern.matcher(value);
            if (!matcher.matches()) {
                throw new MalioValidationException(this.message);
            }
        }
    }

    public void isValid(String value, ValidationResult validationResult) {
        if (value != null) {
                Matcher matcher = this.pattern.matcher(value);
        if (!matcher.matches()) {
            validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
        }}
    }
}
