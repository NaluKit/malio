/*
 * Copyright © 2023 Frank Hossfeld, Philipp Kohl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.nalukit.malio.shared.internal.constraints;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;

public abstract class AbstractMaxLengthConstraint
    extends AbstractConstraint<String> {

  private String message;

  private int maxLength;

  public AbstractMaxLengthConstraint(String packageName,
                                     String simpleName,
                                     String fieldName,
                                     int maxLength) {
    super(packageName,
          simpleName,
          fieldName);
    this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
    this.maxLength = maxLength;
  }

  public void check(String value) throws MalioValidationException {
    if (value != null && value.length() > maxLength) {
      throw new MalioValidationException(this.message);
    }
  }

  public void isValid(String value, ValidationResult validationResult) {
    if (value != null && value.length() > maxLength) {
      validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
    }
  }
}
