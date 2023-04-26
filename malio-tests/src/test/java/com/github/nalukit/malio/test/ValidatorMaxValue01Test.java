package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.maxvalue01.Person;
import com.github.nalukit.malio.test.model.maxvalue01.PersonMalioValidator;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorMaxValue01Test {

    @Test
    public void testCheckOk() throws MalioValidationException {
        Person model = new Person("Name", 18, 0.42f, 10, BigDecimal.valueOf(35000L), 10);
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateOk() {
        Person model = new Person("Name", 18, 0.42f, 10, BigDecimal.valueOf(35000L), 10);

        ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckFail01() {
        Person model = new Person("Name", 112, 0.58f, 500, BigDecimal.valueOf(1_000_000L), 200);

        MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
    }

    @Test
    public void testValidateFail01() {
        Person model = new Person("Name", 112, 0.58f, 500, BigDecimal.valueOf(1_000_000L), 200);

        ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
        assertFalse(validationResult.isValid());
    }
}






