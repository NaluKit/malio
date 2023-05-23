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
package com.github.nalukit.malio.test;

import com.github.nalukit.malio.model.decimalmin01.Person;
import com.github.nalukit.malio.model.decimalmin01.PersonMalioValidator;
import com.github.nalukit.malio.shared.messages.LocalizedMessages;
import com.github.nalukit.malio.shared.messages.locales.MessagesDE;
import com.github.nalukit.malio.shared.messages.locales.MessagesEN;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.MalioValidationException;
import com.google.gwt.junit.client.GWTTestCase;

import java.math.BigDecimal;

public class ValidatorDecimalMin01Test
    extends GWTTestCase {

  @Override
  public void gwtSetUp() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesEN());
  }

  @Override
  public String getModuleName() {
    return "com.github.nalukit.malio.MalioGwt2Test";
  }

  public void testCheckOk()
      throws MalioValidationException {
    Person model = new Person(BigDecimal.valueOf(0.42));
    PersonMalioValidator.INSTANCE.check(model);
  }

  public void testValidateOk() {
    Person model = new Person(BigDecimal.valueOf(0.42));

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckNullOk()
      throws MalioValidationException {
    Person model = new Person(null);
    PersonMalioValidator.INSTANCE.check(model);
  }

  public void testValidateNullOk() {
    Person model = new Person(null);

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckEdgeOk()
      throws MalioValidationException {
    Person model = new Person(BigDecimal.valueOf(0.1));
    PersonMalioValidator.INSTANCE.check(model);
  }

  public void testValidateEdgeOk() {
    Person model = new Person(BigDecimal.valueOf(0.1));

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckFail01() {
    Person model = new Person(BigDecimal.valueOf(0.05));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  public void testValidateFail01() {
    Person model = new Person(BigDecimal.valueOf(0.099999));

    ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(validationResult.isValid());
    assertEquals(1,
                 validationResult.getMessages()
                                 .size());
    assertEquals("Value must not be smaller than 0.1.",
                 validationResult.getMessages()
                                 .get(0)
                                 .getMessage());
  }

  public void testValidateFail01German() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Person model = new Person(BigDecimal.valueOf(0.099999));

    ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(validationResult.isValid());
    assertEquals(1,
                 validationResult.getMessages()
                                 .size());
    assertEquals("Wert darf nicht kleiner als 0.1 sein.",
                 validationResult.getMessages()
                                 .get(0)
                                 .getMessage());
  }

  public void testValidateFail01MessageOverride() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Person model = new Person(BigDecimal.valueOf(2),
                              BigDecimal.valueOf(0.099999));

    ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(validationResult.isValid());
    assertEquals(1,
                 validationResult.getMessages()
                                 .size());
    assertEquals("Override",
                 validationResult.getMessages()
                                 .get(0)
                                 .getMessage());
  }
}






