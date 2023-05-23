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

import com.github.nalukit.malio.model.notnull01.Address;
import com.github.nalukit.malio.model.notnull01.Person;
import com.github.nalukit.malio.model.notnull01.PersonMalioValidator;
import com.github.nalukit.malio.shared.messages.LocalizedMessages;
import com.github.nalukit.malio.shared.messages.locales.MessagesEN;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.MalioValidationException;
import com.google.gwt.junit.client.GWTTestCase;

public class ValidatorNotNull01Test
    extends GWTTestCase {

  @Override
  public void gwtSetUp() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesEN());
  }

  @Override
  public String getModuleName() {
    return "com.github.nalukit.malio.MalioGwt2Test";
  }

  public void testCheckOk() {
    Person model = new Person("Flintstones",
                              "Fred",
                              new Address("Test Avenue 21",
                                          "123456",
                                          "Test City"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
    } catch (MalioValidationException e) {
      fail();
    }
  }

  public void testValidateOk() {
    Person model = new Person("Flintstones",
                              "Fred",
                              new Address("Test Avenue 21",
                                          "123456",
                                          "Test City"));
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckFail01() {
    Person model = new Person(null,
                              "Fred",
                              new Address("Test Avenue 21",
                                          "123456",
                                          "Test City"));

    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
    }
  }

  public void testCheckFail02() {
    Person model = new Person(null,
                              null,
                              new Address("Test Avenue 21",
                                          "123456",
                                          "Test City"));

    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
    }
  }

  public void testCheckFail03() {
    Person model = new Person("Fred",
                              "Flintstones",
                              new Address(null,
                                          "123456",
                                          "Test City"));

    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
    }
  }

  public void testValidateFail01() {
    Person model = new Person(null,
                              "Fred",
                              new Address("Test Avenue 21",
                                          "123456",
                                          "Test City"));

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(1,
                 result.getMessages()
                       .size());
    ErrorMessage errorMessage = result.getMessages()
                                      .get(0);
    assertEquals("com.github.nalukit.malio.model.notnull01.Person",
                 errorMessage.getClassname());
    assertEquals("Person",
                 errorMessage.getSimpleClassname());
    assertEquals("name",
                 errorMessage.getField());
    assertEquals("Object must not be null!",
                 errorMessage.getMessage());
  }

  public void testValidateFail02() {
    Person model = new Person(null,
                              null,
                              new Address("Test Avenue 21",
                                          "123456",
                                          "Test City"));

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(2,
                 result.getMessages()
                       .size());

    ErrorMessage errorMessage01 = result.getMessages()
                                        .get(0);
    assertEquals("com.github.nalukit.malio.model.notnull01.Person",
                 errorMessage01.getClassname());
    assertEquals("Person",
                 errorMessage01.getSimpleClassname());
    assertEquals("name",
                 errorMessage01.getField());
    assertEquals("Object must not be null!",
                 errorMessage01.getMessage());

    ErrorMessage errorMessage02 = result.getMessages()
                                        .get(1);
    assertEquals("com.github.nalukit.malio.model.notnull01.Person",
                 errorMessage02.getClassname());
    assertEquals("Person",
                 errorMessage02.getSimpleClassname());
    assertEquals("firstName",
                 errorMessage02.getField());
    assertEquals("Object must not be null!",
                 errorMessage02.getMessage());
  }

  public void testValidateFail03() {
    Person model = new Person("Fred",
                              "Flintstones",
                              new Address(null,
                                          "123456",
                                          "Test City"));

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(1,
                 result.getMessages()
                       .size());

    ErrorMessage errorMessage01 = result.getMessages()
                                        .get(0);
    assertEquals("com.github.nalukit.malio.model.notnull01.Address",
                 errorMessage01.getClassname());
    assertEquals("Address",
                 errorMessage01.getSimpleClassname());
    assertEquals("street",
                 errorMessage01.getField());
    assertEquals("Object must not be null!",
                 errorMessage01.getMessage());
  }

  public void testValidateFail03MessageOverride() {
    Person model = new Person("Fred",
                              "Flintstones",
                              new Address("safasf",
                                          "123456",
                                          "Test City"),
                              null);

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(1,
                 result.getMessages()
                       .size());

    ErrorMessage errorMessage01 = result.getMessages()
                                        .get(0);
    assertEquals("com.github.nalukit.malio.model.notnull01.Person",
                 errorMessage01.getClassname());
    assertEquals("Person",
                 errorMessage01.getSimpleClassname());
    assertEquals("override",
                 errorMessage01.getField());
    assertEquals("Override",
                 errorMessage01.getMessage());
  }

}
