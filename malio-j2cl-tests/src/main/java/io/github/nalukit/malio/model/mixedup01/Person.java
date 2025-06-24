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
package io.github.nalukit.malio.model.mixedup01;

import io.github.nalukit.malio.shared.annotation.MalioValidator;
import io.github.nalukit.malio.shared.annotation.field.MaxLength;
import io.github.nalukit.malio.shared.annotation.field.NotNull;

@MalioValidator
public class Person {

  @NotNull
  @MaxLength(128)
  private String name;

  @NotNull
  @MaxLength(128)
  private String firstName;

  @NotNull
  private Address address;

  public Person() {
  }

  public Person(String name,
                String firstName,
                Address address) {
    this.name      = name;
    this.firstName = firstName;
    this.address   = address;
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

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}
