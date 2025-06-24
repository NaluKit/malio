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
import io.github.nalukit.malio.model.validateitem01.Address;
import io.github.nalukit.malio.model.validateitem01.Person;
import io.github.nalukit.malio.model.validateitem01.PersonMalioValidator;
import io.github.nalukit.malio.shared.messages.LocalizedMessages;
import io.github.nalukit.malio.shared.messages.locales.MessagesEN;
import io.github.nalukit.malio.shared.model.ValidationResult;
import io.github.nalukit.malio.shared.util.MalioValidationException;

public class ValidateItem01Test
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
                              new Address[] { new Address("street01",
                                                          "zip01",
                                                          "city01"),
                                              new Address("street02",
                                                          "zip02",
                                                          "city02") });
    try {
      PersonMalioValidator.INSTANCE.check(model);
    } catch (MalioValidationException e) {
      fail();
    }
  }

  public void testValidateOk01() {
    Person model = new Person("Firestone",
                              "Fred",
                              new Address[] { new Address("street01",
                                                          "zip01",
                                                          "city01"),
                                              new Address("street02",
                                                          "zip02",
                                                          "city02") });
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  public void testCheckFailed01() {
    Person model = new Person("Firestone",
                              "Fred",
                              new Address[] { new Address("street01",
                                                          "zip01",
                                                          "city01"),
                                              new Address("street02",
                                                          null,
                                                          "city02") });
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  public void testValidateFailed01() {
    Person model = new Person("Firestone",
                              "Fred",
                              new Address[] { new Address("street01",
                                                          "zip01",
                                                          "city01"),
                                              new Address("street02",
                                                          null,
                                                          "city02") });
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(result.isValid());
  }

}
