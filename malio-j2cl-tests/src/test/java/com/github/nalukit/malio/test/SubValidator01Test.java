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

import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.MalioValidationException;
import com.github.nalukit.malio.test.model.subvalidator01.Address;
import com.github.nalukit.malio.test.model.subvalidator01.Person;
import com.github.nalukit.malio.test.model.subvalidator01.PersonMalioValidator;
import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@J2clTestInput(SubValidator01Test.class)
public class SubValidator01Test {

  @Test
  public void testCheckOk() {
    Person model = new Person("Flintstones",
                              "Fred",
                              new Address("Test Avenue 21",
                                          "123456",
                                          "Test City"));

    try {
      PersonMalioValidator.INSTANCE.check(model);
    } catch (MalioValidationException e) {
      fail();
    }
  }

  @Test
  public void testValidateOk() {
    Person           model  = new Person("Flintstones",
                                         "Fred",
                                         new Address("Test Avenue 21",
                                                     "123456",
                                                     "Test City"));
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  @Test
  public void testCheckFail01() {
    Person model = new Person("Flintstones",
                              "Fred",
                              null);

    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
    }
  }

  @Test
  public void testCheckFail02() {
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

  @Test
  public void testValidateFail01() {
    Person model = new Person("Flintstones",
                              "Fred",
                              null);

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(1,
                 result.getMessages()
                       .size());

    ErrorMessage errorMessage01 = result.getMessages()
                                        .get(0);
    assertEquals("com.github.nalukit.malio.test.model.subvalidator01.Person",
                 errorMessage01.getClassname());
    assertEquals("Person",
                 errorMessage01.getSimpleClassname());
    assertEquals("address",
                 errorMessage01.getField());
    assertEquals("n/a",
                 errorMessage01.getMessage());
  }

  @Test
  public void testValidateFail02() {
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
    assertEquals("com.github.nalukit.malio.test.model.subvalidator01.Address",
                 errorMessage01.getClassname());
    assertEquals("Address",
                 errorMessage01.getSimpleClassname());
    assertEquals("street",
                 errorMessage01.getField());
    assertEquals("n/a",
                 errorMessage01.getMessage());
  }

}
