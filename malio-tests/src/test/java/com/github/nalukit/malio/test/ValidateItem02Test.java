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

import com.github.nalukit.malio.model.validateitem02.Person;
import com.github.nalukit.malio.model.validateitem02.PersonMalioValidator;
import com.github.nalukit.malio.shared.messages.LocalizedMessages;
import com.github.nalukit.malio.shared.messages.locales.MessagesEN;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.MalioValidationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateItem02Test {

  @Before
  public void setup() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesEN());
  }

  @Test
  public void testCheckOk()
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
                                          8 });
    PersonMalioValidator.INSTANCE.check(model);
  }

  @Test
  public void testValidateOk() {
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
                                          8 });
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  //  @Test
  //  public void testCheckFail()
  //      throws MalioValidationException {
  //    Person model = new Person("Firestone",
  //                              "Fred",
  //                              new Address[] { new Address("street01",
  //                                                          "zip01",
  //                                                          "city01"),
  //                                              new Address("street02",
  //                                                          null,
  //                                                          "city02") });
  //    MalioValidationException thrown = assertThrows(MalioValidationException.class,
  //                                                   () -> PersonMalioValidator.INSTANCE.check(model));
  //  }
  //
  //  @Test
  //  public void testValidateFail() {
  //    Person model = new Person("Firestone",
  //                              "Fred",
  //                              new Address[] { new Address("street01",
  //                                                          "zip01",
  //                                                          "city01"),
  //                                              new Address("street02",
  //                                                          null,
  //                                                          "city02") });
  //
  //    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
  //    assertFalse(result.isValid());
  //  }
}






