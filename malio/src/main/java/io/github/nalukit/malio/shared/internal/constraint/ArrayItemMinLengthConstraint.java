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

public class ArrayItemMinLengthConstraint
    extends AbstractConstraint<String> {

  private final int minLength;

  public ArrayItemMinLengthConstraint(String packageName,
                                      String simpleName,
                                      String fieldName,
                                      int minLength,
                                      String message) {
    super(packageName,
          simpleName,
          fieldName,
          message);
    this.minLength = minLength;
  }

  public void check(String value)
      throws MalioValidationException {
    if (value != null && value.length() < minLength) {
      throw new MalioValidationException(getMessage(value),
                                         super.getClassName(),
                                         super.getSimpleName(),
                                         super.getFieldName());
    }
  }

  public void isValid(String value,
                      ValidationResult validationResult) {
    if (value != null && value.length() < minLength) {
      validationResult.getMessages()
                      .add(new ErrorMessage(getMessage(value),
                                            super.getClassName(),
                                            super.getSimpleName(),
                                            super.getFieldName()));
    }
  }

  @Override
  protected String getSpecializedMessage(String value) {
    return LocalizedMessages.INSTANCE.getMinLengthMessage(minLength);
  }
}
