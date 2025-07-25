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

import io.github.nalukit.malio.model.validateitem03.Person;
import io.github.nalukit.malio.model.validateitem03.PersonMalioValidator;
import io.github.nalukit.malio.shared.messages.LocalizedMessages;
import io.github.nalukit.malio.shared.messages.locales.MessagesEN;
import io.github.nalukit.malio.shared.model.ValidationResult;
import io.github.nalukit.malio.shared.util.MalioValidationException;
import com.google.gwt.junit.client.GWTTestCase;

import java.util.Arrays;

public class ValidateItem03Test
    extends GWTTestCase {

  @Override
  public void gwtSetUp() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesEN());
  }

  @Override
  public String getModuleName() {
    return "io.github.nalukit.malio.MalioGwt2Test";
  }

  public void testCheckOk01() {
    Person model = new Person("Firestone",
                              "Fred",
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
    } catch (MalioValidationException e) {
      fail();
    }
  }

  public void testValidateOk01() {
    Person model = new Person("Firestone",
                              "Fred",
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckFail01()
      throws MalioValidationException {
    Person model = new Person(null,
                              "Fred",
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Object must not be null!",
                   e.getMessage());
    }
  }

  public void testCheckFail02()
      throws MalioValidationException {
    Person model = new Person("Firestone-0123456789-0123456789-0123456789-0123456789-0123456789-0123456789-0123456789-0123456789",
                              "Fred",
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Value must not be longer than 64.",
                   e.getMessage());
    }
  }

  public void testCheckFail03()
      throws MalioValidationException {
    Person model = new Person("",
                              "Fred",
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("String must not be empty.",
                   e.getMessage());
    }
  }

  public void testCheckFail04()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              null,
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Object must not be null!",
                   e.getMessage());
    }
  }

  public void testCheckFail05()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "",
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Value must not be shorter than 2.",
                   e.getMessage());
    }
  }

  public void testCheckFail06()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred-0123456789-0123456789-0123456789-0123456789-0123456789-0123456789-0123456789-0123456789-0123456789-0123456789",
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Value must not be longer than 64.",
                   e.getMessage());
    }
  }

  public void testCheckFail07()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred",
                              null,
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Object must not be null!",
                   e.getMessage());
    }
  }

  public void testCheckFail08()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred",
                              "coder-0123456789-0123456789-0123456789-0123456789-0123456789-0123456789-0123456789-0123456789",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Value must not be longer than 64.",
                   e.getMessage());
    }
  }

  public void testCheckFail09()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred",
                              "KI",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Value must not be shorter than 4.",
                   e.getMessage());
    }
  }

  public void testCheckFail10()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred",
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03",
                                             "entity04",
                                             "entity05",
                                             "entity06",
                                             "entity07",
                                             "entity08",
                                             "entity09",
                                             "entity10" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Collection size must be between 1 and 8!",
                   e.getMessage());
    }
  }

  public void testCheckFail11()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred",
                              "coder",
                              new String[] { "01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Value must not be shorter than 4.",
                   e.getMessage());
    }
  }

  public void testCheckFail12()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred",
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Value must not be longer than 16.",
                   e.getMessage());
    }
  }

  public void testCheckFail13()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred",
                              "coder",
                              new String[] { "entity01",
                                             "",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Value must not be shorter than 4.",
                   e.getMessage());
    }
  }

  public void testCheckFail14()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred",
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          2,
                                          3,
                                          4,
                                          5,
                                          6,
                                          7,
                                          8,
                                          9 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Collection size must be between 1 and 8!",
                   e.getMessage());
    }
  }

  public void testCheckFail15()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred",
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03",
                                            "entity04",
                                            "entity05",
                                            "entity06",
                                            "entity07",
                                            "entity08",
                                            "entity09",
                                            "entity10",
                                            "entity11"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Collection size must be between 1 and 8!",
                   e.getMessage());
    }
  }

  public void testCheckFail16()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred",
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("01",
                                            "entity02",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Value must not be shorter than 4.",
                   e.getMessage());
    }
  }

  public void testCheckFail17()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred",
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "entity02",
                                            "entity03-0123456789-0123456789-0123456789-0123456789"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Value must not be longer than 16.",
                   e.getMessage());
    }
  }

  public void testCheckFail18()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred",
                              "coder",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8 },
                              Arrays.asList("entity01",
                                            "",
                                            "entity03"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
      assertEquals("Value must not be shorter than 4.",
                   e.getMessage());
    }
  }

  public void testValidateFail01() {
    Person model = new Person("Firestone",
                              "Fred",
                              null,
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03-0123456789-0123456789-0123456789-0123456789-0123456789-0123456789" },
                              new int[] { 1,
                                          1,
                                          2,
                                          3,
                                          5,
                                          8,
                                          13,
                                          21,
                                          34,
                                          55,
                                          89,
                                          144,
                                          233,
                                          377,
                                          610 },
                              Arrays.asList("entity01",
                                            "",
                                            "entity03"));
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(result.isValid());
    assertEquals(5,
                 result.getMessages()
                       .size());
  }
}
