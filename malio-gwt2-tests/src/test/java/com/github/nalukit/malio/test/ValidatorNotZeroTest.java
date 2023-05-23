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

import com.github.nalukit.malio.model.notzero.Person;
import com.github.nalukit.malio.model.notzero.PersonMalioValidator;
import com.github.nalukit.malio.shared.messages.LocalizedMessages;
import com.github.nalukit.malio.shared.messages.locales.MessagesDE;
import com.github.nalukit.malio.shared.messages.locales.MessagesEN;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.MalioValidationException;
import com.google.gwt.junit.client.GWTTestCase;

import java.util.List;

public class ValidatorNotZeroTest
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
    Person model = new Person(1,
                              2121,
                              86,
                              46L);
    PersonMalioValidator.INSTANCE.check(model);
  }

  public void testValidateOk() {
    Person model = new Person(1,
                              2121,
                              -86,
                              46L);
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckNullOk()
      throws MalioValidationException {
    Person model = new Person(1,
                              2121,
                              86,
                              null);
    PersonMalioValidator.INSTANCE.check(model);
  }

  public void testValidateNullOk() {
    Person model = new Person(1,
                              2121,
                              86,
                              null);

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckFail01() {
    Person model = new Person(0,
                              2121,
                              86,
                              null);

    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException exception) {
      // We want to have the exception :)
    }
  }

  public void testValidateFail01() {
    Person model = new Person(0,
                              -2121,
                              86,
                              null);

    ValidationResult   validationResult = PersonMalioValidator.INSTANCE.validate(model);
    List<ErrorMessage> messages         = validationResult.getMessages();
    ErrorMessage       errorMessage     = messages.get(0);

    assertFalse(validationResult.isValid());
    assertEquals(2,
                 messages.size());
    assertEquals("Value must not be zero/or smaller than zero.",
                 errorMessage.getMessage());
  }

  public void testValidateFail01German() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Person model = new Person(0,
                              2121,
                              86,
                              null);

    ValidationResult   validationResult = PersonMalioValidator.INSTANCE.validate(model);
    List<ErrorMessage> messages         = validationResult.getMessages();
    ErrorMessage       errorMessage     = messages.get(0);

    assertFalse(validationResult.isValid());
    assertEquals(1,
                 messages.size());
    assertEquals("Wert muss ungleich/größer als 0 sein!",
                 errorMessage.getMessage());
  }

  public void testValidateFail01MessageOverride() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Person model = new Person(12,
                              2121,
                              86,
                              null,
                              0);

    ValidationResult   validationResult = PersonMalioValidator.INSTANCE.validate(model);
    List<ErrorMessage> messages         = validationResult.getMessages();
    ErrorMessage       errorMessage     = messages.get(0);

    assertFalse(validationResult.isValid());
    assertEquals(1,
                 messages.size());
    assertEquals("Override",
                 errorMessage.getMessage());
  }
}






