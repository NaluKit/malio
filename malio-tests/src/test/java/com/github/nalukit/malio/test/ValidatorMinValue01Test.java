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

import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.shared.util.MalioValidationException;
import com.github.nalukit.malio.test.model.minvalue01.Person;
import com.github.nalukit.malio.test.model.minvalue01.PersonMalioValidator;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorMinValue01Test {

    @Test
    public void testCheckOk() throws MalioValidationException {
        Person model = new Person("Name", 20, 6);
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateOk() {
        Person model = new Person("Name", 20, 5);

        ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckEdgeOk() throws MalioValidationException {
        Person model = new Person("Name", 18, 5);
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateEdgeOk() {
        Person model = new Person("Name", 18, 5);

        ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckNullOk() throws MalioValidationException {
        Person model = new Person("Name", 20, null);
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateNullOk() {
        Person model = new Person("Name", 20, null);

        ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckFail01() {
        Person model = new Person("Name", 10, 4);

        MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
    }

    @Test
    public void testValidateFail01() {
        Person model = new Person("Name", 10, 3);

        ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
        assertFalse(validationResult.isValid());
    }
}






