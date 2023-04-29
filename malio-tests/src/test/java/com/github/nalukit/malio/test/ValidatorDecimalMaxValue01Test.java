package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.decimalmaxvalue01.Person;
import com.github.nalukit.malio.test.model.decimalmaxvalue01.PersonMalioValidator;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorDecimalMaxValue01Test {

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
    public void testCheckEdgeOk() throws MalioValidationException {
        Person model = new Person(BigDecimal.valueOf(0.5));
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateEdgeOk() {
        Person model = new Person(BigDecimal.valueOf(0.5));

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
        Person model = new Person(BigDecimal.valueOf(0.5000001));

        MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
    }

    @Test
    public void testValidateFail01() {
        Person model = new Person(BigDecimal.valueOf(0.6));

        ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
        assertFalse(validationResult.isValid());
    }
}






