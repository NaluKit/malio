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
package com.github.nalukit.malio.model.validateitem03;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.ArraySize;
import com.github.nalukit.malio.shared.annotation.field.MaxLength;
import com.github.nalukit.malio.shared.annotation.field.MinLength;
import com.github.nalukit.malio.shared.annotation.field.NotBlank;
import com.github.nalukit.malio.shared.annotation.field.NotNull;

import java.util.List;

@MalioValidator
public class Person
    extends AbstractPerson {

  @NotNull
  @MaxLength(64)
  @MinLength(1)
  private String profession;

  @ArraySize(min = 1, max = 8)
  @NotNull
  @NotBlank
  @MaxLength(128)
  private List<String> entities;

  public Person() {
  }

  public Person(String firstName,
                String name,
                String profession,
                List<String> entities) {
    super(firstName,
          name);
    this.profession = profession;
    this.entities   = entities;
  }

  public String getProfession() {
    return profession;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public List<String> getEntities() {
    return entities;
  }

  public void setEntities(List<String> entities) {
    this.entities = entities;
  }
}
