package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.minlength01.Address;
import com.github.nalukit.malio.test.model.minlength01.AddressMalioValidator;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorRegexpTest {

    @Test
    public void testCheckOk() throws MalioValidationException {
        Address model = new Address("Street", "12345", "City");
        AddressMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateOk() {
        Address model = new Address("Street", "12345", "City");

        ValidationResult result = AddressMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckFail01() {
        Address model = new Address("Street", "123", "City");

        MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> AddressMalioValidator.INSTANCE.check(model));
    }

    @Test
    public void testValidateFail01() {
        Address model = new Address("Street", "123", "City");

        ValidationResult validationResult = AddressMalioValidator.INSTANCE.validate(model);
        assertFalse(validationResult.isValid());
    }
}






