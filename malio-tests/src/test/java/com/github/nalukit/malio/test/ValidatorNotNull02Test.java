package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.notnull02.Person;
import com.github.nalukit.malio.test.model.notnull02.PersonMalioValidator;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ValidatorNotNull02Test {

  @Test
  public void testCheckOk() {
    Person model = new Person("Flintstones", "Fred");

    try {
      PersonMalioValidator.INSTANCE.check(model);
    } catch (MalioValidationException e) {
      fail();
    }
  }

  @Test
  public void testValidateOk() {
    Person           model  = new Person("Flintstones", "Fred");
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  @Test
  public void testCheckFail01() {
    Person model = new Person(null, "Fred");

    MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
//    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  }

  @Test
  public void testCheckFail02() {
    Person model = new Person(null, null);

    MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
//    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  }

  @Test
  public void testValidateFail01() {
    Person model = new Person(null, "Fred");

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(1,
                 result.getMessages()
                       .size());
    ErrorMessage errorMessage = result.getMessages()
                                     .get(0);
    assertEquals("com.github.nalukit.malio.test.model.notnull02.AbstractPerson",
                 errorMessage .getClassname());
    assertEquals("AbstractPerson",
                 errorMessage  .getSimpleClassname());
    assertEquals("name",
                 errorMessage   .getField());
    assertEquals("n/a",
                 errorMessage    .getMessage());
  }

  @Test
  public void testValidateFail02() {
    Person model = new Person(null, null);

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(2,
                 result.getMessages()
                       .size());

    ErrorMessage errorMessage01 = result.getMessages()
                                     .get(0);
    assertEquals("com.github.nalukit.malio.test.model.notnull02.Person",
                 errorMessage01 .getClassname());
    assertEquals("Person",
                 errorMessage01  .getSimpleClassname());
    assertEquals("firstName",
                 errorMessage01   .getField());
    assertEquals("n/a",
                 errorMessage01    .getMessage());

    ErrorMessage errorMessage02 = result.getMessages()
                                     .get(1);
    assertEquals("com.github.nalukit.malio.test.model.notnull02.AbstractPerson",
                 errorMessage02 .getClassname());
    assertEquals("AbstractPerson",
                 errorMessage02  .getSimpleClassname());
    assertEquals("name",
                 errorMessage02   .getField());
    assertEquals("n/a",
                 errorMessage02    .getMessage());
  }



}
