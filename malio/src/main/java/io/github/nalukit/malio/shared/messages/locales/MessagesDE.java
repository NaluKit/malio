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
package io.github.nalukit.malio.shared.messages.locales;

import io.github.nalukit.malio.shared.messages.IMessages;

import java.math.BigDecimal;

public class MessagesDE
    implements IMessages {

  @Override
  public String getBlacklistMessage(String value) {
    return "String '{0}' ist nicht erlaubt!";
  }

  @Override
  public String getEmailMessage() {
    return "String repräsentiert keine E-Mail Adresse!";
  }

  @Override
  public String getMaxDecimalValueMessage(BigDecimal max) {
    return "Wert darf nicht größer als {0} sein.";
  }

  @Override
  public String getMaxLengthMessage(int length) {
    return "Wert darf nicht länger als {0} sein.";
  }

  @Override
  public String getMaxValueMessage(Long max) {
    return "Wert darf nicht größer als {0} sein.";
  }

  @Override
  public String getMinDecimalValueMessage(BigDecimal min) {
    return "Wert darf nicht kleiner als {0} sein.";
  }

  @Override
  public String getMinLengthMessage(int min) {
    return "Wert darf nicht kürzer als {0} sein.";
  }

  @Override
  public String getMinValueMessage(Long min) {
    return "Wert darf nicht kleiner als {0} sein.";
  }

  @Override
  public String getNotBlankMessage() {
    return "String darf nicht leer sein.";
  }

  @Override
  public String getNotEmptyMessage() {
    return "Collection darf nicht leer sein!";
  }

  @Override
  public String getNotNullMessage() {
    return "Objekt darf nicht null sein!";
  }

  @Override
  public String getRegexpMessage(String value) {
    return "String '{0}' ist nicht erlaubt!";
  }

  @Override
  public String getSizeMessage(int min,
                               int max) {
    return "Collection Länge muss zwischen {0} und {1} sein!";
  }

  @Override
  public String getArraySizeMessage(int min,
                                    int max) {
    return "Array Länge muss zwischen {0} und {1} sein!";
  }

  @Override
  public String getUuidMessage() {
    return "String repräsentiert keine UUID!";
  }

  @Override
  public String getWhitelistMessage(String value) {
    return "String '{0}' ist nicht erlaubt!";
  }

  @Override
  public String getNotZeroMessage() {
    return "Wert muss ungleich/größer als 0 sein!";
  }
}
