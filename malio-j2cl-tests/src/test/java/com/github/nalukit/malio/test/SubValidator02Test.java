package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.subvalidator02.Person;
import com.github.nalukit.malio.test.model.subvalidator02.PersonMalioValidator;
import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@J2clTestInput(SubValidator02Test.class)
public class SubValidator02Test {

  @Test
  public void testCheckOk() {
    Person model = new Person("Flintstones",
                              "Fred");

    try {
      PersonMalioValidator.INSTANCE.check(model);
    } catch (MalioValidationException e) {
      fail();
    }
  }

  @Test
  public void testValidateOk() {
    Person model = new Person("Flintstones",
                              "Fred");
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

}
