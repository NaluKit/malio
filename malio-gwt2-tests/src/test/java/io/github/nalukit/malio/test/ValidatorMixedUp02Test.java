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

import io.github.nalukit.malio.model.mixedup02.Address;
import io.github.nalukit.malio.model.mixedup02.Employee;
import io.github.nalukit.malio.model.mixedup02.EmployeeMalioValidator;
import io.github.nalukit.malio.shared.model.ErrorMessage;
import io.github.nalukit.malio.shared.model.ValidationResult;
import io.github.nalukit.malio.shared.util.MalioValidationException;
import com.google.gwt.junit.client.GWTTestCase;

public class ValidatorMixedUp02Test
    extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "io.github.nalukit.malio.MalioGwt2Test";
  }

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

  public void testValidateOk02() {
    Employee model = new Employee("Flintstones",
                                  "Fred",
                                  new Address("Test Avenue 21",
                                              "123456",
                                              "Test City"),
                                  "coder");
    ValidationResult result = EmployeeMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckFail01() {
    Employee model = new Employee(null,
                                  "Fred",
                                  new Address("Test Avenue 21",
                                              "123456",
                                              "Test City"),
                                  "coder");

    try {
      EmployeeMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  public void testCheckFail02() {
    Employee model = new Employee(null,
                                  null,
                                  new Address("Test Avenue 21",
                                              "123456",
                                              "Test City"),
                                  "coder");

    try {
      EmployeeMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  public void testCheckFail03() {
    Employee model = new Employee("Fred",
                                  "Flintstones",
                                  new Address(null,
                                              "123456",
                                              "Test City"),
                                  "coder");
    try {
      EmployeeMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  public void testCheckFail04() {
    Employee model = new Employee("Fred",
                                  "Flintstones",
                                  new Address("Test Avenue 21",
                                              "123456",
                                              "Test City"),
                                  null);

    try {
      EmployeeMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

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
    assertEquals("io.github.nalukit.malio.model.mixedup02.Person",
                 errorMessage.getClassname());
    assertEquals("Person",
                 errorMessage.getSimpleClassname());
    assertEquals("name",
                 errorMessage.getField());
    assertEquals("Objekt darf nicht null sein!",
                 errorMessage.getMessage());
  }

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
    assertEquals("io.github.nalukit.malio.model.mixedup02.Person",
                 errorMessage01.getClassname());
    assertEquals("Person",
                 errorMessage01.getSimpleClassname());
    assertEquals("name",
                 errorMessage01.getField());
    assertEquals("Objekt darf nicht null sein!",
                 errorMessage01.getMessage());

    ErrorMessage errorMessage02 = result.getMessages()
                                        .get(1);
    assertEquals("io.github.nalukit.malio.model.mixedup02.Person",
                 errorMessage02.getClassname());
    assertEquals("Person",
                 errorMessage02.getSimpleClassname());
    assertEquals("firstName",
                 errorMessage02.getField());
    assertEquals("Objekt darf nicht null sein!",
                 errorMessage02.getMessage());
  }

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
    assertEquals("io.github.nalukit.malio.model.mixedup02.Address",
                 errorMessage01.getClassname());
    assertEquals("Address",
                 errorMessage01.getSimpleClassname());
    assertEquals("street",
                 errorMessage01.getField());
    assertEquals("Objekt darf nicht null sein!",
                 errorMessage01.getMessage());
  }

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
    assertEquals("io.github.nalukit.malio.model.mixedup02.Employee",
                 errorMessage01.getClassname());
    assertEquals("Employee",
                 errorMessage01.getSimpleClassname());
    assertEquals("profession",
                 errorMessage01.getField());
    assertEquals("Objekt darf nicht null sein!",
                 errorMessage01.getMessage());
  }
}
