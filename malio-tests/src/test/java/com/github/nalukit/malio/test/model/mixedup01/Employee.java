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
package com.github.nalukit.malio.test.model.mixedup01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.MaxLength;
import com.github.nalukit.malio.shared.annotation.field.NotNull;

@MalioValidator
public class Employee
  extends Person {

  @NotNull
  @MaxLength(64)
  private String profession;

  public Employee() {
  }

  public Employee(String name,
                  String firstName,
                  Address address,
                  String profession) {
    super.setName(name);
    super.setFirstName(firstName);
    super.setAddress(address);
    this.profession = profession;
  }

  public String getProfession() {
    return profession;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }
}

