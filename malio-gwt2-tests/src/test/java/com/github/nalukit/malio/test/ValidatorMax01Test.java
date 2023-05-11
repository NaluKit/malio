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

import com.github.nalukit.malio.shared.messages.LocalizedMessages;
import com.github.nalukit.malio.shared.messages.locales.MessagesDE;
import com.github.nalukit.malio.shared.messages.locales.MessagesEN;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.MalioValidationException;
import com.github.nalukit.malio.test.model.max01.Person;
import com.github.nalukit.malio.test.model.max01.PersonMalioValidator;
import com.google.gwt.junit.client.GWTTestCase;
import org.junit.Test;

public class ValidatorMax01Test
    extends GWTTestCase {

  @Override
  public void gwtSetUp() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesEN());
  }

  @Override
  public String getModuleName() {
    return "com.github.nalukit.malio.MalioGwt2Test";
  }

  @Test
  public void testCheckOk()
      throws MalioValidationException {
    Person model = new Person("Name",
                              18,
                              10,
                              10);
    PersonMalioValidator.INSTANCE.check(model);
  }

  @Test
  public void testValidateOk() {
    Person model = new Person("Name",
                              18,
                              10,
                              10);

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  @Test
  public void testCheckEdgeOk()
      throws MalioValidationException {
    Person model = new Person("Name",
                              99,
                              99,
                              123);
    PersonMalioValidator.INSTANCE.check(model);
  }

  @Test
  public void testValidateEdgeOk() {
    Person model = new Person("Name",
                              99,
                              99,
                              123);

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  @Test
  public void testCheckNullOk()
      throws MalioValidationException {
    Person model = new Person("Name",
                              18,
                              10,
                              null);
    PersonMalioValidator.INSTANCE.check(model);
  }

  @Test
  public void testValidateNullOk() {
    Person model = new Person("Name",
                              18,
                              10,
                              null);

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  @Test
  public void testCheckFail01() {
    Person model = new Person("Name",
                              112,
                              500,
                              200);

    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  @Test
  public void testValidateFail01() {
    Person model = new Person("Name",
                              112,
                              500,
                              200);

    ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(validationResult.isValid());
    assertEquals(3,
                 validationResult.getMessages()
                                 .size());
    assertEquals("Value must not be greater than 99.",
                 validationResult.getMessages()
                                 .get(0)
                                 .getMessage());
  }

  @Test
  public void testValidateFail01German() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Person model = new Person("Name",
                              112,
                              500,
                              200);

    ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(validationResult.isValid());
    assertEquals(3,
                 validationResult.getMessages()
                                 .size());
    assertEquals("Wert darf nicht größer als 99 sein.",
                 validationResult.getMessages()
                                 .get(0)
                                 .getMessage());
  }
}





