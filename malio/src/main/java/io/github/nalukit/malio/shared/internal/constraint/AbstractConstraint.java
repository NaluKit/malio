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

public abstract class AbstractConstraint<T>
    implements IsMalioConstraint<T> {

  private String packageName;
  private String simpleName;
  private String fieldName;

  private String userMessage;

  private AbstractConstraint() {
  }

  public AbstractConstraint(String packageName,
                            String simpleName,
                            String fieldName,
                            String userMessage) {
    this.packageName = packageName;
    this.simpleName  = simpleName;
    this.fieldName   = fieldName;
    this.userMessage = userMessage;
  }

  protected String getPackageName() {
    return packageName;
  }

  protected String getSimpleName() {
    return simpleName;
  }

  protected String getFieldName() {
    return fieldName;
  }

  protected String getClassName() {
    return this.packageName + "." + this.simpleName;
  }

  protected String getUserMessage() {
    return this.userMessage;
  }

  protected final String getMessage(T value) {
    if (getUserMessage().isEmpty()) {
      return getSpecializedMessage(value);
    }

    return getUserMessage();
  }

  protected abstract String getSpecializedMessage(T value);

}
