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
package com.github.nalukit.malio.model.email01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.Email;

@MalioValidator
public class Person {

  @Email
  private String email;
  @Email(message = "Override")
  private String emailPrivate;

  public Person() {
  }

  public Person(String email,
                String emailPrivate) {
    this.email        = email;
    this.emailPrivate = emailPrivate;
  }

  public Person(String email) {
    this.email = email;
  }

  public String getEmailPrivate() {
    return emailPrivate;
  }

  public void setEmailPrivate(String emailPrivate) {
    this.emailPrivate = emailPrivate;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
