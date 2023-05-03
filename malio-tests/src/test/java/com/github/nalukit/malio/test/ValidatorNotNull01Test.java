package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.notnull01.Address;
import com.github.nalukit.malio.test.model.notnull01.Person;
import com.github.nalukit.malio.test.model.notnull01.PersonMalioValidator;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorNotNull01Test {

  @Test
  public void testCheckOk() {
    Person model = new Person("Flintstones", "Fred", new Address("Test Avenue 21", "123456", "Test City"));

    try {
      PersonMalioValidator.INSTANCE.check(model);
    } catch (MalioValidationException e) {
      fail();
    }
  }

  @Test
  public void testValidateOk() {
    Person           model  = new Person("Flintstones", "Fred", new Address("Test Avenue 21", "123456", "Test City"));
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  @Test
  public void testCheckFail01() {
    Person model = new Person(null, "Fred", new Address("Test Avenue 21", "123456", "Test City"));

    MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
//    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  }

  @Test
  public void testCheckFail02() {
    Person model = new Person(null, null, new Address("Test Avenue 21", "123456", "Test City"));

    MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
//    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  }


  @Test
  public void testCheckFail03() {
    Person model = new Person("Fred", "Flintstones", new Address(null, "123456", "Test City"));

    MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
//    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  }

  @Test
  public void testValidateFail01() {
    Person model = new Person(null, "Fred", new Address("Test Avenue 21", "123456", "Test City"));

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(1,
                 result.getMessages()
                       .size());
    ErrorMessage errorMessage = result.getMessages()
                                     .get(0);
    assertEquals("com.github.nalukit.malio.test.model.notnull01.Person",
                 errorMessage .getClassname());
    assertEquals("Person",
                 errorMessage  .getSimpleClassname());
    assertEquals("name",
                 errorMessage   .getField());
    assertEquals("n/a",
                 errorMessage    .getMessage());
  }

  @Test
  public void testValidateFail02() {
    Person model = new Person(null, null, new Address("Test Avenue 21", "123456", "Test City"));

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(2,
                 result.getMessages()
                       .size());

    ErrorMessage errorMessage01 = result.getMessages()
                                     .get(0);
    assertEquals("com.github.nalukit.malio.test.model.notnull01.Person",
                 errorMessage01 .getClassname());
    assertEquals("Person",
                 errorMessage01  .getSimpleClassname());
    assertEquals("name",
                 errorMessage01   .getField());
    assertEquals("n/a",
                 errorMessage01    .getMessage());

    ErrorMessage errorMessage02 = result.getMessages()
                                     .get(1);
    assertEquals("com.github.nalukit.malio.test.model.notnull01.Person",
                 errorMessage02 .getClassname());
    assertEquals("Person",
                 errorMessage02  .getSimpleClassname());
    assertEquals("firstName",
                 errorMessage02   .getField());
    assertEquals("n/a",
                 errorMessage02    .getMessage());
  }

  @Test
  public void testValidateFail03() {
    Person model = new Person("Fred", "Flintstones", new Address(null, "123456", "Test City"));

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(1,
                 result.getMessages()
                       .size());

    ErrorMessage errorMessage01 = result.getMessages()
                                     .get(0);
    assertEquals("com.github.nalukit.malio.test.model.notnull01.Address",
                 errorMessage01 .getClassname());
    assertEquals("Address",
                 errorMessage01  .getSimpleClassname());
    assertEquals("street",
                 errorMessage01   .getField());
    assertEquals("n/a",
                 errorMessage01    .getMessage());
  }



}
