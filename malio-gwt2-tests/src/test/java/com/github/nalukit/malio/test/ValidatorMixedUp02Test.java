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
import com.github.nalukit.malio.test.model.mixedup02.Address;
import com.github.nalukit.malio.test.model.mixedup02.Employee;
import com.github.nalukit.malio.test.model.mixedup02.EmployeeMalioValidator;
import com.google.gwt.junit.client.GWTTestCase;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class ValidatorMixedUp02Test
    extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "com.github.nalukit.malio.MalioGwt2Test";
  }

  @Test
  public void testCheckOk02() {
    Employee model = new Employee("Flintstones",
                                  "Fred",
                                  new Address("Test Avenue 21",
                                              "123456",
                                              "Test City"),
                                  "coder");

    try {
      EmployeeMalioValidator.INSTANCE.check(model);
    } catch (MalioValidationException e) {
      fail();
    }
  }

  @Test
  public void testValidateOk02() {
    Employee         model  = new Employee("Flintstones",
                                           "Fred",
                                           new Address("Test Avenue 21",
                                                       "123456",
                                                       "Test City"),
                                           "coder");
    ValidationResult result = EmployeeMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  @Test
  public void testCheckFail01() {
    Employee model = new Employee(null,
                                  "Fred",
                                  new Address("Test Avenue 21",
                                              "123456",
                                              "Test City"),
                                  "coder");

    MalioValidationException thrown = assertThrows(MalioValidationException.class,
                                                   () -> EmployeeMalioValidator.INSTANCE.check(model));
    //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  }

  @Test
  public void testCheckFail02() {
    Employee model = new Employee(null,
                                  null,
                                  new Address("Test Avenue 21",
                                              "123456",
                                              "Test City"),
                                  "coder");

    MalioValidationException thrown = assertThrows(MalioValidationException.class,
                                                   () -> EmployeeMalioValidator.INSTANCE.check(model));
    //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  }

  @Test
  public void testCheckFail03() {
    Employee model = new Employee("Fred",
                                  "Flintstones",
                                  new Address(null,
                                              "123456",
                                              "Test City"),
                                  "coder");

    MalioValidationException thrown = assertThrows(MalioValidationException.class,
                                                   () -> EmployeeMalioValidator.INSTANCE.check(model));
    //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  }

  @Test
  public void testCheckFail04() {
    Employee model = new Employee("Fred",
                                  "Flintstones",
                                  new Address("Test Avenue 21",
                                              "123456",
                                              "Test City"),
                                  null);

    MalioValidationException thrown = assertThrows(MalioValidationException.class,
                                                   () -> EmployeeMalioValidator.INSTANCE.check(model));
    //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  }

  @Test
  public void testValidateFail01() {
    Employee model = new Employee(null,
                                  "Fred",
                                  new Address("Test Avenue 21",
                                              "123456",
                                              "Test City"),
                                  "coder");

    ValidationResult result = EmployeeMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(1,
                 result.getMessages()
                       .size());
    ErrorMessage errorMessage = result.getMessages()
                                      .get(0);
    assertEquals("com.github.nalukit.malio.test.model.mixedup02.Person",
                 errorMessage.getClassname());
    assertEquals("Person",
                 errorMessage.getSimpleClassname());
    assertEquals("name",
                 errorMessage.getField());
    assertEquals("n/a",
                 errorMessage.getMessage());
  }

  @Test
  public void testValidateFail02() {
    Employee model = new Employee(null,
                                  null,
                                  new Address("Test Avenue 21",
                                              "123456",
                                              "Test City"),
                                  "coder");

    ValidationResult result = EmployeeMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(2,
                 result.getMessages()
                       .size());

    ErrorMessage errorMessage01 = result.getMessages()
                                        .get(0);
    assertEquals("com.github.nalukit.malio.test.model.mixedup02.Person",
                 errorMessage01.getClassname());
    assertEquals("Person",
                 errorMessage01.getSimpleClassname());
    assertEquals("name",
                 errorMessage01.getField());
    assertEquals("n/a",
                 errorMessage01.getMessage());

    ErrorMessage errorMessage02 = result.getMessages()
                                        .get(1);
    assertEquals("com.github.nalukit.malio.test.model.mixedup02.Person",
                 errorMessage02.getClassname());
    assertEquals("Person",
                 errorMessage02.getSimpleClassname());
    assertEquals("firstName",
                 errorMessage02.getField());
    assertEquals("n/a",
                 errorMessage02.getMessage());
  }

  @Test
  public void testValidateFail03() {
    Employee model = new Employee("Fred",
                                  "Flintstones",
                                  new Address(null,
                                              "123456",
                                              "Test City"),
                                  "ccoer");

    ValidationResult result = EmployeeMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(1,
                 result.getMessages()
                       .size());

    ErrorMessage errorMessage01 = result.getMessages()
                                        .get(0);
    assertEquals("com.github.nalukit.malio.test.model.mixedup02.Address",
                 errorMessage01.getClassname());
    assertEquals("Address",
                 errorMessage01.getSimpleClassname());
    assertEquals("street",
                 errorMessage01.getField());
    assertEquals("n/a",
                 errorMessage01.getMessage());
  }

  @Test
  public void testValidateFail04() {
    Employee model = new Employee("Fred",
                                  "Flintstones",
                                  new Address("Test Avenue 21",
                                              "123456",
                                              "Test City"),
                                  null);

    ValidationResult result = EmployeeMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(1,
                 result.getMessages()
                       .size());

    ErrorMessage errorMessage01 = result.getMessages()
                                        .get(0);
    assertEquals("com.github.nalukit.malio.test.model.mixedup02.Employee",
                 errorMessage01.getClassname());
    assertEquals("Employee",
                 errorMessage01.getSimpleClassname());
    assertEquals("profession",
                 errorMessage01.getField());
    assertEquals("n/a",
                 errorMessage01.getMessage());
  }
}
