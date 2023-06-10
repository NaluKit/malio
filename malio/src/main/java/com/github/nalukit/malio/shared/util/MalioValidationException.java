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
package com.github.nalukit.malio.shared.util;

public class MalioValidationException
    extends RuntimeException {

  private String packageName;
  private String simpleName;
  private String fieldName;

  public MalioValidationException() {
  }

  public MalioValidationException(String message,
                                  String packageName,
                                  String simpleName,
                                  String fieldName) {
    super(message);
    this.packageName = packageName;
    this.simpleName  = simpleName;
    this.fieldName   = fieldName;
  }

  public String getPackageName() {
    return this.packageName;
  }

  public String getSimpleName() {
    return this.simpleName;
  }

  public String getFieldName() {
    return this.fieldName;
  }

  public String getClassName() {
    return this.packageName + "." + this.simpleName;
  }

}
