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
package io.github.nalukit.malio.model.decimalmax01;

import io.github.nalukit.malio.shared.annotation.MalioValidator;
import io.github.nalukit.malio.shared.annotation.field.DecimalMax;

import java.math.BigDecimal;

@MalioValidator
public class Person {

  @DecimalMax("0.5")
  private BigDecimal taxRate;

  @DecimalMax(value = "10", message = "Override")
  private BigDecimal these;

  public Person() {
  }

  public Person(BigDecimal taxRate) {
    this.taxRate = taxRate;
  }

  public Person(BigDecimal taxRate,
                BigDecimal these) {
    this.taxRate = taxRate;
    this.these   = these;
  }

  public BigDecimal getThese() {
    return these;
  }

  public void setThese(BigDecimal these) {
    this.these = these;
  }

  public BigDecimal getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(BigDecimal taxRate) {
    this.taxRate = taxRate;
  }
}
