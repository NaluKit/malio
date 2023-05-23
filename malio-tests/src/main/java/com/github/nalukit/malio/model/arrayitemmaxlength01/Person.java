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
package com.github.nalukit.malio.model.arrayitemmaxlength01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.ArrayItemMaxLength;

@MalioValidator
public class Person
    extends AbstractPerson {

  @ArrayItemMaxLength(16) private String[] entities;

  public Person() {
  }

  public Person(String firstName,
                String name,
                String[] entities) {
    super(firstName,
          name);
    this.entities = entities;
  }

  public String[] getEntities() {
    return entities;
  }

  public void setEntities(String[] entities) {
    this.entities = entities;
  }

}
