
package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.internal.annotation.MalioInternalUse;
import com.github.nalukit.malio.shared.model.ValidationResult;

@MalioInternalUse
public interface IsMalioConstraint<T> {

 void check(T value) throws MalioValidationException;

  void  isValid(T value, ValidationResult validationResult);

}
