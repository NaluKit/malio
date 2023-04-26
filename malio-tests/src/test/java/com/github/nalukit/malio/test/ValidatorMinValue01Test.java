package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.minvalue01.Person;
import com.github.nalukit.malio.test.model.minvalue01.PersonMalioValidator;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorMinValue01Test {

    @Test
    public void testCheckOk() throws MalioValidationException {
        Person model = new Person("Name", 20, 0.42f, BigDecimal.valueOf(35000L), 6);
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateOk() {
        Person model = new Person("Name", 20, 0.42f, BigDecimal.valueOf(35000L), 5);

        ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckFail01() {
        Person model = new Person("Name", 10, 0.58f, BigDecimal.valueOf(1_000_000L), 4);

        MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
    }

    @Test
    public void testValidateFail01() {
        Person model = new Person("Name", 10, 0.58f, BigDecimal.valueOf(1_000_000L), 3);

        ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
        assertFalse(validationResult.isValid());
    }
}






