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

import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.MalioValidationException;

import java.util.Objects;

public abstract class AbstractNotBlankConstraint
    extends AbstractConstraint<String> {

  private String message;

  public AbstractNotBlankConstraint(String packageName,
                                    String simpleName,
                                    String fieldName) {
    super(packageName,
          simpleName,
          fieldName);
    this.message = "n/a"; // TODO aus Factory unter Verwendung des Locale holen
  }

  public void check(String value) throws MalioValidationException {
    if (Objects.nonNull(value) && value.isEmpty()) {
      throw new MalioValidationException(this.message);
    }
  }

  public void  isValid(String value, ValidationResult validationResult) {
    if (Objects.nonNull(value)  && value.isEmpty()) {
      validationResult.getMessages().add(new ErrorMessage(this.message, super.getClassName(), super.getSimpleName(), super.getFieldName()));
    }
  }
}
