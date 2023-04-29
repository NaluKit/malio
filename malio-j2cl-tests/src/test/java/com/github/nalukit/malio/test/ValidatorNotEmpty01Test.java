package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.notempty01.Person;
import com.github.nalukit.malio.test.model.notempty01.PersonMalioValidator;
import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

@J2clTestInput(ValidatorNotEmpty01Test.class)
public class ValidatorNotEmpty01Test {

    @Test
    public void testCheckOk() throws MalioValidationException {
        Person model = new Person(Arrays.asList("Card", "Mobile Phone"));
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateOk() {
        Person model = new Person(Arrays.asList("Card", "Mobile Phone"));

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
        Person model = new Person(new ArrayList<>());

        MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
    }

    @Test
    public void testValidateFail01() {
        Person model = new Person(new ArrayList<>());

        ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
        assertFalse(validationResult.isValid());
        assertEquals(1, validationResult.getMessages().size());
    }
}






