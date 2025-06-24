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
import io.github.nalukit.malio.model.notblank01.Person;
import io.github.nalukit.malio.model.notblank01.PersonMalioValidator;
import io.github.nalukit.malio.shared.messages.LocalizedMessages;
import io.github.nalukit.malio.shared.messages.locales.MessagesDE;
import io.github.nalukit.malio.shared.messages.locales.MessagesEN;
import io.github.nalukit.malio.shared.model.ValidationResult;
import io.github.nalukit.malio.shared.util.MalioValidationException;

public class ValidatorNotBlank01Test
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
    Person model = new Person("Simpson",
                              "Bart");
    PersonMalioValidator.INSTANCE.check(model);
  }

  public void testValidateOk() {
    Person model = new Person("Simpson",
                              "Bart");

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckNullOk()
      throws MalioValidationException {
    Person model = new Person(null,
                              null);
    PersonMalioValidator.INSTANCE.check(model);
  }

  public void testValidateNullOk() {
    Person model = new Person(null,
                              null);

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckFail01() {
    Person model = new Person("",
                              "Bart");

    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  public void testValidateFail01() {
    Person model = new Person("",
                              "");

    ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(validationResult.isValid());
    assertEquals(2,
                 validationResult.getMessages()
                                 .size());
    assertEquals("String must not be empty.",
                 validationResult.getMessages()
                                 .get(0)
                                 .getMessage());
  }

  public void testValidateFail01German() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Person model = new Person("",
                              "");

    ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(validationResult.isValid());
    assertEquals(2,
                 validationResult.getMessages()
                                 .size());
    assertEquals("String darf nicht leer sein.",
                 validationResult.getMessages()
                                 .get(0)
                                 .getMessage());
  }

  public void testValidateFail01MessageOverride() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Person model = new Person("gfsdg",
                              "fdgdfg",
                              "");

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






