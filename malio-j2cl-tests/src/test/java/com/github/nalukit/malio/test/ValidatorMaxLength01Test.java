package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.maxlength01.Address;
import com.github.nalukit.malio.test.model.maxlength01.AddressMalioValidator;
import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Test;

import static org.junit.Assert.*;

@J2clTestInput(ValidatorMaxLength01Test.class)
public class ValidatorMaxLength01Test {

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
    public void testCheckNullOk() throws MalioValidationException {
        Address model = new Address(null, "12345", "City");
        AddressMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateNullOk() {
        Address model = new Address(null, "12345", "City");

        ValidationResult result = AddressMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckFail01() {
        Address model = new Address("Street", "123456", "City");

        MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> AddressMalioValidator.INSTANCE.check(model));
    }

    @Test
    public void testValidateFail01() {
        Address model = new Address("Street", "123456", "City");

        ValidationResult validationResult = AddressMalioValidator.INSTANCE.validate(model);
        assertFalse(validationResult.isValid());
    }
}






