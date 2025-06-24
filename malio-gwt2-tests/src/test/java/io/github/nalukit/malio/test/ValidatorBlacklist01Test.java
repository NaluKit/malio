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
import io.github.nalukit.malio.model.blacklist01.Address;
import io.github.nalukit.malio.model.blacklist01.AddressMalioValidator;
import io.github.nalukit.malio.shared.messages.LocalizedMessages;
import io.github.nalukit.malio.shared.messages.locales.MessagesDE;
import io.github.nalukit.malio.shared.messages.locales.MessagesEN;
import io.github.nalukit.malio.shared.model.ErrorMessage;
import io.github.nalukit.malio.shared.model.ValidationResult;
import io.github.nalukit.malio.shared.util.MalioValidationException;

import java.util.List;

public class ValidatorBlacklist01Test
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
    Address model = new Address("Street",
                                "13579",
                                "City");
    AddressMalioValidator.INSTANCE.check(model);
  }

  public void testValidateOk() {
    Address model = new Address("Street",
                                "13579",
                                "City");

    ValidationResult result = AddressMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckFail01() {
    Address model = new Address("Secret",
                                "123",
                                "City");

    try {
      AddressMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  public void testCheckNull()
      throws MalioValidationException {
    Address model = new Address(null,
                                "123",
                                "City");
    AddressMalioValidator.INSTANCE.check(model);
  }

  public void testValidateNull()
      throws MalioValidationException {
    Address model = new Address(null,
                                "123",
                                "City");
    ValidationResult result = AddressMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testValidateFail01() {
    Address model = new Address("Secret",
                                "12345",
                                "City");

    ValidationResult validationResult = AddressMalioValidator.INSTANCE.validate(model);
    assertFalse(validationResult.isValid());
    assertEquals(2,
                 validationResult.getMessages()
                                 .size());
    assertEquals("String 'Secret' is not allowed!",
                 validationResult.getMessages()
                                 .get(0)
                                 .getMessage());

  }

  public void testValidateFail01German() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Address model = new Address("Secret",
                                "12345",
                                "City");

    ValidationResult   validationResult = AddressMalioValidator.INSTANCE.validate(model);
    List<ErrorMessage> messages         = validationResult.getMessages();
    ErrorMessage       errorMessage     = messages.get(0);

    assertFalse(validationResult.isValid());
    assertEquals(2,
                 messages.size());
    assertEquals("String 'Secret' ist nicht erlaubt!",
                 errorMessage.getMessage());
  }

  public void testValidateFailMessageOverride() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Address model = new Address("Hello",
                                "1245",
                                "City",
                                "123");

    ValidationResult   validationResult = AddressMalioValidator.INSTANCE.validate(model);
    List<ErrorMessage> messages         = validationResult.getMessages();
    ErrorMessage       errorMessage     = messages.get(0);

    assertFalse(validationResult.isValid());
    assertEquals(1,
                 messages.size());
    assertEquals("Override",
                 errorMessage.getMessage());
  }
}






