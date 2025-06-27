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
package io.github.nalukit.malio.model.validateitem03;

import io.github.nalukit.malio.shared.annotation.MalioValidator;
import io.github.nalukit.malio.shared.annotation.field.ArrayItemMaxLength;
import io.github.nalukit.malio.shared.annotation.field.ArrayItemMinLength;
import io.github.nalukit.malio.shared.annotation.field.ArrayItemNotBlank;
import io.github.nalukit.malio.shared.annotation.field.ArrayItemNotNull;
import io.github.nalukit.malio.shared.annotation.field.ArraySize;
import io.github.nalukit.malio.shared.annotation.field.CollectionItemMaxLength;
import io.github.nalukit.malio.shared.annotation.field.CollectionItemMinLength;
import io.github.nalukit.malio.shared.annotation.field.CollectionItemNotBlank;
import io.github.nalukit.malio.shared.annotation.field.CollectionItemNotNull;
import io.github.nalukit.malio.shared.annotation.field.MaxLength;
import io.github.nalukit.malio.shared.annotation.field.MinLength;
import io.github.nalukit.malio.shared.annotation.field.NotNull;
import io.github.nalukit.malio.shared.annotation.field.Size;

import java.util.List;

@MalioValidator
public class Person
    extends AbstractPerson {

  @NotNull
  @MaxLength(64)
  @MinLength(4)
  private String profession;

  @ArraySize(min = 1, max = 8)
  @ArrayItemNotNull
  @ArrayItemMinLength(4)
  @ArrayItemMaxLength(16)
  @ArrayItemNotBlank
  private String[] entities;

  @ArraySize(min = 1, max = 8)
  private int[] myNumbers;

  @Size(min = 1, max = 8)
  @CollectionItemNotNull
  @CollectionItemMinLength(4)
  @CollectionItemMaxLength(16)
  @CollectionItemNotBlank
  private List<String> pockets;

  public Person() {
  }

  public Person(String firstName,
                String name,
                String profession,
                String[] entities,
                int[] myNumbers,
                List<String> pockets) {
    super(firstName,
          name);
    this.profession = profession;
    this.entities   = entities;
    this.myNumbers  = myNumbers;
    this.pockets    = pockets;
  }

  public String getProfession() {
    return profession;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public String[] getEntities() {
    return entities;
  }

  public void setEntities(String[] entities) {
    this.entities = entities;
  }

  public int[] getMyNumbers() {
    return myNumbers;
  }

  public void setMyNumbers(int[] myNumbers) {
    this.myNumbers = myNumbers;
  }

  public List<String> getPockets() {
    return pockets;
  }

  public void setPockets(List<String> pockets) {
    this.pockets = pockets;
  }
}
