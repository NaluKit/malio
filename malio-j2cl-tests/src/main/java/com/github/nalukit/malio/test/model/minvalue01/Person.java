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
package com.github.nalukit.malio.test.model.minvalue01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.MinValue;

@MalioValidator
public class Person {

  private String name;

  @MinValue(18) private int age;

  @MinValue(5) private Integer complexTypeTest;

  public Person() {
  }

  public Person(String name,
                int age,
                Integer complexTypeTest) {
    this.name            = name;
    this.age             = age;
    this.complexTypeTest = complexTypeTest;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Integer getComplexTypeTest() {
    return complexTypeTest;
  }

  public void setComplexTypeTest(Integer complexTypeTest) {
    this.complexTypeTest = complexTypeTest;
  }
}
