package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.notblank01.Person;
import com.github.nalukit.malio.test.model.notblank01.PersonMalioValidator;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorNotBlank01Test {

    @Test
    public void testCheckOk() throws MalioValidationException {
        Person model = new Person("Simpson", "Bart");
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateOk() {
        Person model = new Person("Simpson", "Bart");

        ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckFail01() {
        Person model = new Person("", "Bart");

        MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
    }

    @Test
    public void testValidateFail01() {
        Person model = new Person("", "");

        ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
        assertFalse(validationResult.isValid());
        assertEquals(2, validationResult.getMessages().size());
    }
}






