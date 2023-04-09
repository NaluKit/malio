package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.maxlength01.Address;
import com.github.nalukit.malio.test.model.maxlength01.AddressMalioValidator;
import com.github.nalukit.malio.test.model.notnull01.Person;
import com.github.nalukit.malio.test.model.notnull01.PersonMalioValidator;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorMaxLength01Test {

    @Test
    public void testCheckOk() {
        Address model = new Address("Street", "12345", "City");

        try {
            AddressMalioValidator.INSTANCE.check(model);
        } catch (MalioValidationException e) {
            fail();
        }
    }

    @Test
    public void testValidateOk() {
        Address model = new Address("Street", "12345", "City");

        ValidationResult result = AddressMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckFail01() {
        Address model = new Address("Street", "123456", "City");

        MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> AddressMalioValidator.INSTANCE.check(model));
//    assertTrue(thrown.getMessage().contentEquals("asd sad "));
    }

    @Test
    public void testValidateFail01() {
        Address model = new Address("Street", "123456", "City");

        ValidationResult validationResult = AddressMalioValidator.INSTANCE.validate(model);
        assertFalse(validationResult.isValid());
    }
}






