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

import com.github.nalukit.malio.model.notnull04.Person;
import com.github.nalukit.malio.model.notnull04.PersonMalioValidator;
import com.github.nalukit.malio.shared.messages.LocalizedMessages;
import com.github.nalukit.malio.shared.messages.locales.MessagesDE;
import com.github.nalukit.malio.shared.messages.locales.MessagesEN;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.MalioValidationException;
import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@J2clTestInput(ValidatorNotNull04Test.class)
public class ValidatorNotNull04Test {

  @Before
  public void setup() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesEN());
  }

  @Test
  public void testCheckOk() {
    Person model = new Person("Flintstones",
                              "Fred");

    try {
      PersonMalioValidator.INSTANCE.check(model);
    } catch (MalioValidationException e) {
      fail();
    }
  }

  @Test
  public void testValidateOk() {
    Person model = new Person("Flintstones",
                              "Fred");
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  @Test
  public void testCheckFail01() {
    Person model = new Person(null,
                              "Fred");

    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
    }
    //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  }

  @Test
  public void testCheckFail02() {
    Person model = new Person(null,
                              null);

    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
    }
    //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  }

  @Test
  public void testValidateFail01() {
    Person model = new Person(null,
                              "Fred");

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(1,
                 result.getMessages()
                       .size());
    ErrorMessage errorMessage = result.getMessages()
                                      .get(0);
    assertEquals("com.github.nalukit.malio.model.notnull04.helper.AbstractPerson",
                 errorMessage.getClassname());
    assertEquals("AbstractPerson",
                 errorMessage.getSimpleClassname());
    assertEquals("name",
                 errorMessage.getField());
    assertEquals("Object must not be null!",
                 errorMessage.getMessage());
  }

  @Test
  public void testValidateFail02() {
    Person model = new Person(null,
                              null);

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(2,
                 result.getMessages()
                       .size());

    ErrorMessage errorMessage01 = result.getMessages()
                                        .get(0);
    assertEquals("com.github.nalukit.malio.model.notnull04.Person",
                 errorMessage01.getClassname());
    assertEquals("Person",
                 errorMessage01.getSimpleClassname());
    assertEquals("firstName",
                 errorMessage01.getField());
    assertEquals("Object must not be null!",
                 errorMessage01.getMessage());

    ErrorMessage errorMessage02 = result.getMessages()
                                        .get(1);
    assertEquals("com.github.nalukit.malio.model.notnull04.helper.AbstractPerson",
                 errorMessage02.getClassname());
    assertEquals("AbstractPerson",
                 errorMessage02.getSimpleClassname());
    assertEquals("name",
                 errorMessage02.getField());
    assertEquals("Object must not be null!",
                 errorMessage02.getMessage());
  }

  @Test
  public void testValidateFail01GermanMessage() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());

    Person model = new Person(null,
                              "Fred");

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(1,
                 result.getMessages()
                       .size());
    ErrorMessage errorMessage = result.getMessages()
                                      .get(0);
    assertEquals("com.github.nalukit.malio.model.notnull04.helper.AbstractPerson",
                 errorMessage.getClassname());
    assertEquals("AbstractPerson",
                 errorMessage.getSimpleClassname());
    assertEquals("name",
                 errorMessage.getField());
    assertEquals("Objekt darf nicht null sein!",
                 errorMessage.getMessage());
  }

}
