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
package com.github.nalukit.malio.model.subvalidator03;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.NotNull;

import java.util.ArrayList;
import java.util.List;

@MalioValidator
public class Person {

  @NotNull
  private String       name;
  @NotNull
  private String       firstName;
  @NotNull
  private Address      address;
  @NotNull
  private List<String> roles;
  @NotNull
  private List<Person> children;

  public Person() {
    this.roles = new ArrayList<>();
  }

  public Person(String name,
                String firstName,
                Address address) {
    this();
    this.name      = name;
    this.firstName = firstName;
    this.address   = address;
  }

  public Person(String name,
                String firstName,
                Address address,
                List<Person> children) {
    this();
    this.name      = name;
    this.firstName = firstName;
    this.address   = address;
    this.children  = children;
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

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public List<Person> getChildren() {
    return children;
  }

  public void setChildren(List<Person> children) {
    this.children = children;
  }
}
