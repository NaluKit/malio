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

import io.github.nalukit.malio.model.size.Person;
import io.github.nalukit.malio.model.size.PersonMalioValidator;
import io.github.nalukit.malio.shared.messages.LocalizedMessages;
import io.github.nalukit.malio.shared.messages.locales.MessagesDE;
import io.github.nalukit.malio.shared.messages.locales.MessagesEN;
import io.github.nalukit.malio.shared.model.ErrorMessage;
import io.github.nalukit.malio.shared.model.ValidationResult;
import io.github.nalukit.malio.shared.util.MalioValidationException;
import com.google.gwt.junit.client.GWTTestCase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ValidatorSize01Test
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
    Person model = new Person(Arrays.asList("Card",
                                            "Mobile Phone"));
    PersonMalioValidator.INSTANCE.check(model);
  }

  public void testValidateOk() {
    Person model = new Person(Arrays.asList("Card",
                                            "Mobile Phone",
                                            "Keys"));

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckNullOk()
      throws MalioValidationException {
    Person model = new Person(null);
    PersonMalioValidator.INSTANCE.check(model);
  }

  public void testValidateNullOk() {
    Person model = new Person(null);

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckFailTooFew() {
    Person model = new Person(Collections.singletonList("Card"));

    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  public void testValidateFailTooMany() {
    Person model = new Person(Arrays.asList("Card",
                                            "Mobile Phone",
                                            "Keys",
                                            "Sun Creme",
                                            "Screws"));

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
    Person model = new Person(Arrays.asList("Card",
                                            "Mobile Phone",
                                            "Keys",
                                            "Sun Creme",
                                            "Screws"));

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
    Person model = new Person(Arrays.asList("Card",
                                            "Mobile Phone",
                                            "Keys",
                                            "Sun Creme"),
                              Arrays.asList("Card",
                                            "Mobile Phone",
                                            "Keys",
                                            "Sun Creme",
                                            "Screws"));

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






