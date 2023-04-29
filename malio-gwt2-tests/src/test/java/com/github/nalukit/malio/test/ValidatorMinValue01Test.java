package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.minvalue01.Person;
import com.github.nalukit.malio.test.model.minvalue01.PersonMalioValidator;
import com.google.gwt.junit.client.GWTTestCase;
import org.junit.Test;
import static org.junit.Assert.assertThrows;


public class ValidatorMinValue01Test extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "com.github.nalukit.malio.MalioGwt2Test";
    }

    @Test
    public void testCheckOk() throws MalioValidationException {
        Person model = new Person("Name", 20, 6);
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateOk() {
        Person model = new Person("Name", 20, 5);

        ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckEdgeOk() throws MalioValidationException {
        Person model = new Person("Name", 18, 5);
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateEdgeOk() {
        Person model = new Person("Name", 18, 5);

        ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckNullOk() throws MalioValidationException {
        Person model = new Person("Name", 20, null);
        PersonMalioValidator.INSTANCE.check(model);
    }

    @Test
    public void testValidateNullOk() {
        Person model = new Person("Name", 20, null);

        ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
        assertTrue(result.isValid());
    }

    @Test
    public void testCheckFail01() {
        Person model = new Person("Name", 10, 4);

        MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
    }

    @Test
    public void testValidateFail01() {
        Person model = new Person("Name", 10, 3);

        ValidationResult validationResult = PersonMalioValidator.INSTANCE.validate(model);
        assertFalse(validationResult.isValid());
    }
}






