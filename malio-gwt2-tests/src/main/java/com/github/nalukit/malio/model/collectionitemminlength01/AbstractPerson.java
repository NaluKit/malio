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
package com.github.nalukit.malio.model.collectionitemminlength01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.MaxLength;
import com.github.nalukit.malio.shared.annotation.field.MinLength;
import com.github.nalukit.malio.shared.annotation.field.NotBlank;
import com.github.nalukit.malio.shared.annotation.field.NotNull;

@MalioValidator
public abstract class AbstractPerson {

  @NotNull
  @MaxLength(64)
  @MinLength(1)
  private String name;

  @NotNull
  @NotBlank
  @MaxLength(64)
  private String firstName;

  public AbstractPerson() {
  }

  public AbstractPerson(String firstName,
                        String name) {
    this.firstName = firstName;
    this.name      = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
}
