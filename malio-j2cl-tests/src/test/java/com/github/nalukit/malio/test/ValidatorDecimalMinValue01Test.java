package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.decimalminvalue01.Person;
import com.github.nalukit.malio.test.model.decimalminvalue01.PersonMalioValidator;
import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@J2clTestInput(ValidatorDecimalMinValue01Test.class)
public class ValidatorDecimalMinValue01Test {

    @Test
    public void testCheckOk() throws MalioValidationException {
        Person model = new Person(BigDecimal.valueOf(0.42));
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateOk() {
        Person model = new Person(BigDecimal.valueOf(0.42));

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
    public void testCheckEdgeOk() throws MalioValidationException {
        Person model = new Person(BigDecimal.valueOf(0.1));
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateEdgeOk() {
        Person model = new Person(BigDecimal.valueOf(0.1));

        ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }
    @Test
    public void testCheckFail01() {
        Person model = new Person(BigDecimal.valueOf(0.05));

        MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
    }

    @Test
    public void testValidateFail01() {
        Person model = new Person(BigDecimal.valueOf(0.099999));

        ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
        assertFalse(validationResult.isValid());
    }
}






