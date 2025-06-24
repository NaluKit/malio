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
import io.github.nalukit.malio.model.maxlength01.Address;
import io.github.nalukit.malio.model.maxlength01.AddressMalioValidator;
import io.github.nalukit.malio.shared.messages.LocalizedMessages;
import io.github.nalukit.malio.shared.messages.locales.MessagesDE;
import io.github.nalukit.malio.shared.messages.locales.MessagesEN;
import io.github.nalukit.malio.shared.model.ValidationResult;
import io.github.nalukit.malio.shared.util.MalioValidationException;

public class ValidatorMaxLength01Test
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
                                "12345",
                                "City");
    AddressMalioValidator.INSTANCE.check(model);
  }

  public void testValidateOk() {
    Address model = new Address("Street",
                                "12345",
                                "City");

    ValidationResult result = AddressMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckNullOk()
      throws MalioValidationException {
    Address model = new Address(null,
                                "12345",
                                "City");
    AddressMalioValidator.INSTANCE.check(model);
  }

  public void testValidateNullOk() {
    Address model = new Address(null,
                                "12345",
                                "City");

    ValidationResult result = AddressMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckFail01() {
    Address model = new Address("Street",
                                "123456",
                                "City");

    try {
      AddressMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  public void testValidateFail01() {
    Address model = new Address("Street",
                                "123456",
                                "City");

    ValidationResult validationResult = AddressMalioValidator.INSTANCE.validate(model);
    assertFalse(validationResult.isValid());
    assertEquals(1,
                 validationResult.getMessages()
                                 .size());
    assertEquals("Value must not be longer than 5.",
                 validationResult.getMessages()
                                 .get(0)
                                 .getMessage());
  }

  public void testValidateFail01German() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Address model = new Address("Street",
                                "123456",
                                "City");

    ValidationResult validationResult = AddressMalioValidator.INSTANCE.validate(model);
    assertFalse(validationResult.isValid());
    assertEquals(1,
                 validationResult.getMessages()
                                 .size());
    assertEquals("Wert darf nicht länger als 5 sein.",
                 validationResult.getMessages()
                                 .get(0)
                                 .getMessage());
  }

  public void testValidateFail01MessageOverride() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
    Address model = new Address("Street",
                                "12345",
                                "City",
                                "123456");

    ValidationResult validationResult = AddressMalioValidator.INSTANCE.validate(model);
    assertFalse(validationResult.isValid());
    assertEquals(1,
                 validationResult.getMessages()
                                 .size());
    assertEquals("Override",
                 validationResult.getMessages()
                                 .get(0)
                                 .getMessage());
  }
}






