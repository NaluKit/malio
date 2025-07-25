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

import io.github.nalukit.malio.model.checkandvalidate01.Person;
import io.github.nalukit.malio.model.checkandvalidate01.PersonMalioValidator;
import io.github.nalukit.malio.shared.messages.LocalizedMessages;
import io.github.nalukit.malio.shared.messages.locales.MessagesEN;
import io.github.nalukit.malio.shared.model.ValidationResult;
import io.github.nalukit.malio.shared.util.MalioValidationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckAndValidate01Test {

  @Before
  public void setup() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesEN());
  }

  @Test
  public void testCheckOk01()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" });
    PersonMalioValidator.INSTANCE.check(model);
  }

  @Test
  public void testValidateOk() {
    Person model = new Person("Firestone",
                              "Fred",
                              new String[] { "entity01",
                                             "entity02",
                                             "entity03" });
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  @Test
  public void testCheckFail01()
      throws MalioValidationException {
    Person model = new Person("Firestone",
                              "Fred",
                              new String[] { "entity01",
                                             "",
                                             "entity03" });
    MalioValidationException thrown = assertThrows(MalioValidationException.class,
                                                   () -> PersonMalioValidator.INSTANCE.check(model));
    assertTrue(thrown.getMessage()
                     .contentEquals("String must not be empty."));
  }

  @Test
  public void testValidateFail01() {
    Person model = new Person("Firestone",
                              "Fred",
                              new String[] { "entity01",
                                             "",
                                             "entity03" });
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(result.isValid());
  }
}






