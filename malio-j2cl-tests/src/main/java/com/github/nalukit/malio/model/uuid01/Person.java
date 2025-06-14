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
package com.github.nalukit.malio.model.uuid01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.Uuid;

@MalioValidator
public class Person {

  @Uuid
  private String uuid;
  @Uuid(message = "Override")
  private String override;

  public Person() {
  }

  public Person(String uuid) {
    this.uuid = uuid;
  }

  public Person(String uuid,
                String override) {
    this.uuid     = uuid;
    this.override = override;
  }

  public String getOverride() {
    return override;
  }

  public void setOverride(String override) {
    this.override = override;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }
}
