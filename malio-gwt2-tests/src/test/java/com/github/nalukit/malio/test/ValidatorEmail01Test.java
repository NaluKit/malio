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

import com.github.nalukit.malio.shared.messages.LocalizedMessages;
import com.github.nalukit.malio.shared.messages.locales.MessagesDE;
import com.github.nalukit.malio.shared.messages.locales.MessagesEN;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.MalioValidationException;
import com.github.nalukit.malio.test.model.email01.Person;
import com.github.nalukit.malio.test.model.email01.PersonMalioValidator;
import com.google.gwt.junit.client.GWTTestCase;
import org.junit.Test;

public class ValidatorEmail01Test extends GWTTestCase {

    @Override
    public void gwtSetUp() {
        LocalizedMessages.INSTANCE.setMessages(new MessagesEN());
    }

    @Override
    public String getModuleName() {
        return "com.github.nalukit.malio.MalioGwt2Test";
    }

    @Test
    public void testCheckOk() throws MalioValidationException {
        Person model = new Person("me@domain.com");
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateOk() {
        Person model = new Person("me@domain.com");

        ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckNullOk() throws MalioValidationException {
        Person model = new Person(null);
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateNullOk() {
        Person model = new Person(null);

        ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckFail01() {
      Person model = new Person("medomain.com");

      try {
        PersonMalioValidator.INSTANCE.check(model);
        fail();
      } catch (MalioValidationException e) {
      }

    }

    @Test
    public void testValidateFail01() {
        Person model = new Person("medomain.com");

        ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
        assertFalse(validationResult.isValid());
        assertEquals(1, validationResult.getMessages().size());
        assertEquals("String does not represent an email address!",
                validationResult.getMessages().get(0).getMessage());
    }

    @Test
    public void testValidateFail01German() {
        LocalizedMessages.INSTANCE.setMessages(new MessagesDE());
        Person model = new Person("medomain.com");

        ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
        assertFalse(validationResult.isValid());
        assertEquals(1, validationResult.getMessages().size());
        assertEquals("String repräsentiert keine E-Mail Adresse!",
                validationResult.getMessages().get(0).getMessage());
    }
}






