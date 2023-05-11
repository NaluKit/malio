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
package com.github.nalukit.malio.model.notzero;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.NotNull;
import com.github.nalukit.malio.shared.annotation.field.NotZero;

import java.util.List;

@MalioValidator
public class Person {


  @NotZero
  private int personNr;

  @NotZero
  private Integer age;

  @NotZero(allowNegativeValues = true)
  private long addressNr;

  @NotZero
  private Long addressNrLong;

  @NotZero(message = "Override")
  private int override;

  public Person() {
  }

  public Person(int personNr, Integer age, long addressNr, Long addressNrLong) {
    this.personNr = personNr;
    this.age = age;
    this.addressNr = addressNr;
    this.addressNrLong = addressNrLong;
    this.override = 10;
  }

  public Person(int personNr, Integer age, long addressNr, Long addressNrLong, int override) {
    this.personNr = personNr;
    this.age = age;
    this.addressNr = addressNr;
    this.addressNrLong = addressNrLong;
    this.override = override;
  }

  public int getOverride() {
    return override;
  }

  public void setOverride(int override) {
    this.override = override;
  }

  public int getPersonNr() {
    return personNr;
  }

  public void setPersonNr(int personNr) {
    this.personNr = personNr;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public long getAddressNr() {
    return addressNr;
  }

  public void setAddressNr(long addressNr) {
    this.addressNr = addressNr;
  }

  public Long getAddressNrLong() {
    return addressNrLong;
  }

  public void setAddressNrLong(Long addressNrLong) {
    this.addressNrLong = addressNrLong;
  }
}
