package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.whitelist01.Address;
import com.github.nalukit.malio.test.model.whitelist01.AddressMalioValidator;
import com.google.gwt.junit.client.GWTTestCase;
import org.junit.Test;
import static org.junit.Assert.assertThrows;


public class ValidatorWhitelistTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "com.github.nalukit.malio.MalioGwt2Test";
    }

    @Test
    public void testCheckOk() throws MalioValidationException {
        Address model = new Address("My Street", "54321", "My Town");
        AddressMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateOk() {
        Address model = new Address("My Street", "54321", "My Town");

        ValidationResult result = AddressMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckNullOk() throws MalioValidationException {
        Address model = new Address(null, null, null);
        AddressMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateNullOk() {
        Address model = new Address(null, null, null);

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
        assertEquals(3, validationResult.getMessages().size());
    }
}






