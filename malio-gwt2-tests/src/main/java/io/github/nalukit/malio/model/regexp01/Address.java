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
package io.github.nalukit.malio.model.regexp01;

import io.github.nalukit.malio.shared.annotation.MalioValidator;
import io.github.nalukit.malio.shared.annotation.field.Regexp;

@MalioValidator
public class Address {

  @Regexp(regexp = ".+ Street")
  private String street;
  @Regexp(regexp = "\\d{5}")
  private String zip;

  @Regexp(regexp = ".+ City")
  private String city;
  @Regexp(regexp = ".+ City", message = "Override")
  private String override;

  public Address() {
  }

  public Address(String street,
                 String zip,
                 String city) {
    this.street = street;
    this.zip    = zip;
    this.city   = city;
  }

  public Address(String street,
                 String zip,
                 String city,
                 String override) {
    this.street   = street;
    this.zip      = zip;
    this.city     = city;
    this.override = override;
  }

  public String getOverride() {
    return override;
  }

  public void setOverride(String override) {
    this.override = override;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }
}
