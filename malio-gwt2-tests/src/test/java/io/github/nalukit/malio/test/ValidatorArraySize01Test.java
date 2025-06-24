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
import io.github.nalukit.malio.model.arraysize.Person;
import io.github.nalukit.malio.model.arraysize.PersonMalioValidator;
import io.github.nalukit.malio.shared.messages.LocalizedMessages;
import io.github.nalukit.malio.shared.messages.locales.MessagesDE;
import io.github.nalukit.malio.shared.messages.locales.MessagesEN;
import io.github.nalukit.malio.shared.model.ErrorMessage;
import io.github.nalukit.malio.shared.model.ValidationResult;
import io.github.nalukit.malio.shared.util.MalioValidationException;

import java.util.List;

public class ValidatorArraySize01Test
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
    Person model = new Person(new String[] { "Card",
                                             "Mobile Phone" },
                              new int[] { 0,
                                          1,
                                          3 });
    PersonMalioValidator.INSTANCE.check(model);
  }

  public void testValidateOk() {
    Person model = new Person(new String[] { "Card",
                                             "Mobile Phone" },
                              new int[] { 0,
                                          1,
                                          3 });

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

  public void testCheckFailTooFew() {
    Person model = new Person(new String[] { "Card", },
                              new int[] { 0,
                                          1,
                                          3 });

    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException exception) {
      // Test should fail!
    }
  }

  public void testValidateFailTooMany() {
    Person model = new Person(new String[] { "Card",
                                             "Mobile Phone",
                                             "These",
                                             "That",
                                             "Those" },
                              new int[] { 0,
                                          1,
                                          3 });

    ValidationResult   validationResult = PersonMalioValidator.INSTANCE.validate(model);
    List<ErrorMessage> messages         = validationResult.getMessages();
    assertFalse(validationResult.isValid());
    assertEquals(1,
                 messages.size());
    assertEquals("Collection size must be between 2 and 4!",
                 messages.get(0)
                         .getMessage());
  }

  public void testValidateFailTooManyGerman() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Person model = new Person(new String[] { "Card",
                                             "Mobile Phone",
                                             "These",
                                             "That",
                                             "Those" },
                              new int[] { 0,
                                          1,
                                          3 });

    ValidationResult   validationResult = PersonMalioValidator.INSTANCE.validate(model);
    List<ErrorMessage> messages         = validationResult.getMessages();
    assertFalse(validationResult.isValid());
    assertEquals(1,
                 messages.size());
    assertEquals("Collection Länge muss zwischen 2 und 4 sein!",
                 messages.get(0)
                         .getMessage());
  }

  public void testValidateFailMessageOverride() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Person model = new Person(new String[] { "Card",
                                             "Mobile Phone",
                                             "These" },
                              new int[] { 0,
                                          1,
                                          3 },
                              new String[] { "Card",
                                             "Mobile Phone",
                                             "These",
                                             "That",
                                             "Those" });

    ValidationResult   validationResult = PersonMalioValidator.INSTANCE.validate(model);
    List<ErrorMessage> messages         = validationResult.getMessages();
    assertFalse(validationResult.isValid());
    assertEquals(1,
                 messages.size());
    assertEquals("Override",
                 messages.get(0)
                         .getMessage());
  }
}






