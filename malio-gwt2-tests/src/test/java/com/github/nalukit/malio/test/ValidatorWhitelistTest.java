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

import com.github.nalukit.malio.test.model.whitelist01.Address;
import com.github.nalukit.malio.shared.messages.LocalizedMessages;
import com.github.nalukit.malio.shared.messages.locales.MessagesDE;
import com.github.nalukit.malio.shared.messages.locales.MessagesEN;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.MalioValidationException;
import com.github.nalukit.malio.test.model.whitelist01.Address;
import com.github.nalukit.malio.test.model.whitelist01.AddressMalioValidator;
import com.google.gwt.junit.client.GWTTestCase;
import org.junit.Test;

import java.util.List;

public class ValidatorWhitelistTest
    extends GWTTestCase {

  @Override
  public void gwtSetUp() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesEN());
  }

  @Override
  public String getModuleName() {
    return "com.github.nalukit.malio.MalioGwt2Test";
  }

  @Test
  public void testCheckOk()
      throws MalioValidationException {
    Address model = new Address("My Street",
                                "54321",
                                "My Town");
    AddressMalioValidator.INSTANCE.check(model);
  }

  @Test
  public void testValidateOk() {
    Address model = new Address("My Street",
                                "54321",
                                "My Town");

    ValidationResult result = AddressMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  @Test
  public void testCheckNullOk()
      throws MalioValidationException {
    Address model = new Address(null,
                                null,
                                null);
    AddressMalioValidator.INSTANCE.check(model);
  }

  @Test
  public void testValidateNullOk() {
    Address model = new Address(null,
                                null,
                                null);

    ValidationResult result = AddressMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  @Test
  public void testCheckFail01() {
    Address model = new Address("Street",
                                "123",
                                "City");

    try {
      AddressMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  @Test
  public void testValidateFail01() {
    Address model = new Address("Street",
                                "123",
                                "City");

    ValidationResult   validationResult = AddressMalioValidator.INSTANCE.validate(model);
    List<ErrorMessage> messages         = validationResult.getMessages();
    ErrorMessage       errorMessage     = messages.get(0);

    assertFalse(validationResult.isValid());
    assertEquals(3,
                 messages.size());
    assertEquals("String 'Street' is not allowed!",
                 errorMessage.getMessage());
  }

  @Test
  public void testValidateFail01German() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Address model = new Address("Street",
                                "123",
                                "City");

    ValidationResult   validationResult = AddressMalioValidator.INSTANCE.validate(model);
    List<ErrorMessage> messages         = validationResult.getMessages();
    ErrorMessage       errorMessage     = messages.get(0);

    assertFalse(validationResult.isValid());
    assertEquals(3,
                 messages.size());
    assertEquals("String 'Street' ist nicht erlaubt!",
                 errorMessage.getMessage());
  }

  @Test
  public void testValidateFail01MessageOverride() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Address model = new Address("My Street",
            "54321",
            "My Town",
            "454");

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






