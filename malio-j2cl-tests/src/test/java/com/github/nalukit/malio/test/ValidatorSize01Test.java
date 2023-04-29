package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.size.Person;
import com.github.nalukit.malio.test.model.size.PersonMalioValidator;
import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

@J2clTestInput(ValidatorSize01Test.class)
public class ValidatorSize01Test {

    @Test
    public void testCheckOk() throws MalioValidationException {
        Person model = new Person(Arrays.asList("Card", "Mobile Phone"));
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateOk() {
        Person model = new Person(Arrays.asList("Card", "Mobile Phone", "Keys"));

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
    public void testCheckFailTooFew() {
        Person model = new Person(Arrays.asList("Card"));

        MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
    }

    @Test
    public void testValidateFailTooMany() {
        Person model = new Person(Arrays.asList("Card", "Mobile Phone", "Keys", "Sun Creme", "Screws"));

        ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
        assertFalse(validationResult.isValid());
        assertEquals(1, validationResult.getMessages().size());
    }
}






