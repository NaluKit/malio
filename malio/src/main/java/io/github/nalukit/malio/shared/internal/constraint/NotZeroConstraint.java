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
package io.github.nalukit.malio.shared.internal.constraint;

import io.github.nalukit.malio.shared.messages.LocalizedMessages;
import io.github.nalukit.malio.shared.model.ErrorMessage;
import io.github.nalukit.malio.shared.model.ValidationResult;
import io.github.nalukit.malio.shared.util.MalioValidationException;

import java.util.Objects;

public class NotZeroConstraint
    extends AbstractConstraint<Number> {

  private final boolean allowNegativeValues;

  public NotZeroConstraint(String packageName,
                           String simpleName,
                           String fieldName,
                           boolean allowNegativeValues,
                           String message) {
    super(packageName,
          simpleName,
          fieldName,
          message);
    this.allowNegativeValues = allowNegativeValues;
  }

  public void check(Number value)
      throws MalioValidationException {
    if (Objects.isNull(value)) {
      return;
    }

    if (!this.allowNegativeValues) {
      if (value.longValue() < 0) {
        throw new MalioValidationException(getMessage(value),
                                           super.getClassName(),
                                           super.getSimpleName(),
                                           super.getFieldName());
      }
    }

    if (value.longValue() == 0) {
      throw new MalioValidationException(getMessage(value),
                                         super.getClassName(),
                                         super.getSimpleName(),
                                         super.getFieldName());
    }
  }

  public void isValid(Number value,
                      ValidationResult validationResult) {
    if (Objects.isNull(value)) {
      return;
    }

    if (!this.allowNegativeValues) {
      if (value.longValue() < 0) {
        validationResult.getMessages()
                        .add(new ErrorMessage(getMessage(value),
                                              super.getClassName(),
                                              super.getSimpleName(),
                                              super.getFieldName()));
        return;
      }
    }

    if (value.longValue() == 0) {
      validationResult.getMessages()
                      .add(new ErrorMessage(getMessage(value),
                                            super.getClassName(),
                                            super.getSimpleName(),
                                            super.getFieldName()));
    }

  }

  @Override
  protected String getSpecializedMessage(Number value) {
    return LocalizedMessages.INSTANCE.getNotZeroMessage();
  }
}
