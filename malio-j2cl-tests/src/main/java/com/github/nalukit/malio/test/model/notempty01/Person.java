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
package com.github.nalukit.malio.test.model.notempty01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.NotEmpty;

import java.util.List;

@MalioValidator
public class Person {

  @NotEmpty private List<String> pocket;
  @NotEmpty(message = "Override") private List<String> override;

  public Person() {
  }

  public Person(List<String> pocket, List<String> override) {
    this.pocket = pocket;
    this.override = override;
  }

  public List<String> getOverride() {
    return override;
  }

  public void setOverride(List<String> override) {
    this.override = override;
  }

  public Person(List<String> things) {
    this.pocket = things;
  }

  public List<String> getPocket() {
    return pocket;
  }

  public void setPocket(List<String> pocket) {
    this.pocket = pocket;
  }
}
