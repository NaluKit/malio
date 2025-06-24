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
package io.github.nalukit.malio.test;

import com.google.gwt.junit.client.GWTTestCase;
import io.github.nalukit.malio.model.min01.Person;
import io.github.nalukit.malio.model.min01.PersonMalioValidator;
import io.github.nalukit.malio.shared.messages.LocalizedMessages;
import io.github.nalukit.malio.shared.messages.locales.MessagesDE;
import io.github.nalukit.malio.shared.messages.locales.MessagesEN;
import io.github.nalukit.malio.shared.model.ValidationResult;
import io.github.nalukit.malio.shared.util.MalioValidationException;

public class ValidatorMin01Test
    extends GWTTestCase {

  @Override
  public void gwtSetUp() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesEN());
  }

  @Override
  public String getModuleName() {
    return "io.github.nalukit.malio.MalioGwt2Test";
  }

  public void testCheckOk()
      throws MalioValidationException {
    Person model = new Person("Name",
                              20,
                              6);
    PersonMalioValidator.INSTANCE.check(model);
  }

  public void testValidateOk() {
    Person model = new Person("Name",
                              20,
                              5);

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckEdgeOk()
      throws MalioValidationException {
    Person model = new Person("Name",
                              18,
                              5);
    PersonMalioValidator.INSTANCE.check(model);
  }

  public void testValidateEdgeOk() {
    Person model = new Person("Name",
                              18,
                              5);

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckNullOk()
      throws MalioValidationException {
    Person model = new Person("Name",
                              20,
                              null);
    PersonMalioValidator.INSTANCE.check(model);
  }

  public void testValidateNullOk() {
    Person model = new Person("Name",
                              20,
                              null);

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckFail01() {
    Person model = new Person("Name",
                              10,
                              4);

    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  public void testValidateFail01() {
    Person model = new Person("Name",
                              10,
                              3);

    ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(validationResult.isValid());
    assertEquals(2,
                 validationResult.getMessages()
                                 .size());
    assertEquals("Value must not be smaller than 18.",
                 validationResult.getMessages()
                                 .get(0)
                                 .getMessage());
  }

  public void testValidateFail01German() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Person model = new Person("Name",
                              10,
                              3);

    ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(validationResult.isValid());
    assertEquals(2,
                 validationResult.getMessages()
                                 .size());
    assertEquals("Wert darf nicht kleiner als 18 sein.",
                 validationResult.getMessages()
                                 .get(0)
                                 .getMessage());
  }

  public void testValidateFail01MessageOverride() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Person model = new Person("Name",
                              20,
                              10,
                              5);

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






