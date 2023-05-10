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

import java.util.Objects;

public class NotZeroConstraint
    extends AbstractConstraint<Number> {

  private boolean allowNegativeValues;
  private String message;
  public NotZeroConstraint(String packageName,
                           String simpleName,
                           String fieldName,
                           boolean allowNegativeValues) {
    super(packageName,
          simpleName,
          fieldName);
    this.message  = LocalizedMessages.INSTANCE.getNotZeroMessage();
    this.allowNegativeValues = allowNegativeValues;
  }

  public void check(Number value)
      throws MalioValidationException {
    if (Objects.isNull(value)) {
      return;
    }

    if (!this.allowNegativeValues) {
      if( value.longValue() < 0){
        throw new MalioValidationException(message);
      }
    }

    if (value.longValue() == 0) {
      throw new MalioValidationException(message);
    }
  }

  public void isValid(Number value,
                      ValidationResult validationResult) {
    if (Objects.isNull(value)) {
      return;
    }


    if (!this.allowNegativeValues) {
      if(value.longValue() < 0){
        validationResult.getMessages()
                .add(new ErrorMessage(message,
                        super.getClassName(),
                        super.getSimpleName(),
                        super.getFieldName()));
        return;
      }
    }

    if (value.longValue() == 0) {
      validationResult.getMessages()
              .add(new ErrorMessage(message,
                      super.getClassName(),
                      super.getSimpleName(),
                      super.getFieldName()));
    }

  }
}
