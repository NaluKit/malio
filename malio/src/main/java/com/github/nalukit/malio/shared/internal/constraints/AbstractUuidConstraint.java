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

import com.github.nalukit.malio.shared.messages.LocalizedMessages;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.MalioValidationException;
import org.gwtproject.regexp.shared.RegExp;

import java.util.Objects;

public abstract class AbstractUuidConstraint
    extends AbstractConstraint<String> {

  private final static RegExp regExp = RegExp.compile("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$");

  public AbstractUuidConstraint(String packageName,
                                String simpleName,
                                String fieldName) {
    super(packageName,
          simpleName,
          fieldName);
  }

  public void check(String value)
      throws MalioValidationException {
    String message = LocalizedMessages.INSTANCE.getUuidMessage();
    if (Objects.nonNull(value) && !AbstractUuidConstraint.regExp.test(value)) {
      throw new MalioValidationException(message);
    }
  }

  public void isValid(String value,
                      ValidationResult validationResult) {
    String message = LocalizedMessages.INSTANCE.getUuidMessage();
    if (Objects.nonNull(value) && !AbstractUuidConstraint.regExp.test(value)) {
      validationResult.getMessages()
                      .add(new ErrorMessage(message,
                                            super.getClassName(),
                                            super.getSimpleName(),
                                            super.getFieldName()));
    }
  }
}