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

import com.google.j2cl.junit.apt.J2clTestInput;
import io.github.nalukit.malio.model.regexp01.Address;
import io.github.nalukit.malio.model.regexp01.AddressMalioValidator;
import io.github.nalukit.malio.shared.messages.LocalizedMessages;
import io.github.nalukit.malio.shared.messages.locales.MessagesDE;
import io.github.nalukit.malio.shared.messages.locales.MessagesEN;
import io.github.nalukit.malio.shared.model.ErrorMessage;
import io.github.nalukit.malio.shared.model.ValidationResult;
import io.github.nalukit.malio.shared.util.MalioValidationException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@J2clTestInput(ValidatorRegexpTest.class)
public class ValidatorRegexpTest {

  @Before
  public void setup() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesEN());
  }

  @Test
  public void testCheckOk()
      throws MalioValidationException {
    Address model = new Address("My Street",
                                "12345",
                                "My City");
    AddressMalioValidator.INSTANCE.check(model);
  }

  @Test
  public void testValidateOk() {
    Address model = new Address("My Street",
                                "12345",
                                "MY City");

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

    MalioValidationException thrown = assertThrows(MalioValidationException.class,
                                                   () -> AddressMalioValidator.INSTANCE.check(model));
  }

  @Test
  public void testValidateFail01() {
    LocalizedMessages.INSTANCE.setMessages(new MessagesEN());
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
    Address model = new Address("Malio Street",
                                "12345",
                                "Malio City",
                                "Bla");

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






