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
package com.github.nalukit.malio.test.model.arraysize;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.ArraySize;

@MalioValidator
public class Person {

  @ArraySize(min = 2, max = 4)
  private String[] pocket;
  @ArraySize(min = 2, max = 4)
  private int[] coins;

  public Person() {
  }

  public Person(String[] things, int[] coins) {
    this.pocket = things;
    this.coins = coins;
  }

  public String[] getPocket() {
    return pocket;
  }

  public void setPocket(String[] pocket) {
    this.pocket = pocket;
  }

  public int[] getCoins() {
    return coins;
  }

  public void setCoins(int[] coins) {
    this.coins = coins;
  }
}
