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

import com.github.nalukit.malio.model.subvalidator04.Person;
import com.github.nalukit.malio.model.subvalidator04.PersonMalioValidator;
import com.github.nalukit.malio.shared.util.MalioValidationException;
import org.junit.Test;

import static org.junit.Assert.fail;

public class SubValidator04Test {

  @Test
  public void testCheckOk01() {
    Person modelParent = new Person("Simpsons",
                                    "Homer");
    Person modelChild = new Person("Simpsons",
                                   "Bart");
    modelChild.setParent(modelParent);
    modelParent.setChild(modelChild);
    try {
      PersonMalioValidator.INSTANCE.check(modelParent);
    } catch (MalioValidationException e) {
      fail();
    }
  }

  //  @Test
  //  public void testValidateOk01() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    model.setChildren(new ArrayList<>());
  //    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
  //    assertTrue(result.isValid());
  //  }
  //
  //  @Test
  //  public void testCheckOk02() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    model.setChildren(new ArrayList<>());
  //    model.getChildren()
  //         .add(new Person("Flintstones",
  //                         "Selma",
  //                         new Address("Test Avenue",
  //                                     "123456",
  //                                     "Test City"),
  //                         new ArrayList<>()));
  //    try {
  //      PersonMalioValidator.INSTANCE.check(model);
  //    } catch (MalioValidationException e) {
  //      fail();
  //    }
  //  }
  //
  //  @Test
  //  public void testValidateOk02() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    model.setChildren(new ArrayList<>());
  //    model.getChildren()
  //         .add(new Person("Flintstones",
  //                         "Selma",
  //                         new Address("Test Avenue",
  //                                     "123456",
  //                                     "Test City"),
  //                         new ArrayList<>()));
  //    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
  //    assertTrue(result.isValid());
  //  }
  //
  //  @Test
  //  public void testCheckFailed01() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    try {
  //      PersonMalioValidator.INSTANCE.check(model);
  //      fail();
  //    } catch (MalioValidationException e) {
  //    }
  //  }
  //
  //  @Test
  //  public void testValidateFailed01() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
  //    assertFalse(result.isValid());
  //  }
  //
  //  @Test
  //  public void testCheckFailed02() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    model.setChildren(new ArrayList<>());
  //    model.getChildren()
  //         .add(new Person(null,
  //                         "Selma",
  //                         new Address("Test Avenue",
  //                                     "123456",
  //                                     "Test City")));
  //    try {
  //      PersonMalioValidator.INSTANCE.check(model);
  //      fail();
  //    } catch (MalioValidationException e) {
  //    }
  //  }
  //
  //  @Test
  //  public void testValidateFailed02() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    model.setChildren(new ArrayList<>());
  //    model.getChildren()
  //         .add(new Person(null,
  //                         "Selma",
  //                         new Address("Test Avenue",
  //                                     "123456",
  //                                     "Test City")));
  //    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
  //    assertFalse(result.isValid());
  //  }
  //
  //  @Test
  //  public void testCheckFailed03() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    model.setChildren(new ArrayList<>());
  //    model.getChildren()
  //         .add(new Person("Flintstones",
  //                         "Selma",
  //                         new Address("Test Avenue",
  //                                     null,
  //                                     "Test City")));
  //    try {
  //      PersonMalioValidator.INSTANCE.check(model);
  //      fail();
  //    } catch (MalioValidationException e) {
  //    }
  //  }
  //
  //  @Test
  //  public void testValidateFailed03() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    model.setChildren(new ArrayList<>());
  //    model.getChildren()
  //         .add(new Person("Flintstones",
  //                         "Selma",
  //                         new Address("Test Avenue",
  //                                     null,
  //                                     "Test City")));
  //    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
  //    assertFalse(result.isValid());
  //  }

}
