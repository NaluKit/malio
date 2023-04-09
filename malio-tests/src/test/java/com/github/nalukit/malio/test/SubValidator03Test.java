package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.subvalidator03.Address;
import com.github.nalukit.malio.test.model.subvalidator03.Person;
import com.github.nalukit.malio.test.model.subvalidator03.PersonMalioValidator;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SubValidator03Test {

  @Test
  public void testCheckOk01() {
    Person model = new Person("Flintstones",
                              "Fred",
                              new Address("Test Avenue",
                                          "123456",
                                          "Test City"));
    model.setChildren(new ArrayList<>());
    try {
      PersonMalioValidator.INSTANCE.check(model);
    } catch (MalioValidationException e) {
      fail();
    }
  }

  @Test
  public void testValidateOk01() {
    Person model = new Person("Flintstones",
                              "Fred",
                              new Address("Test Avenue",
                                          "123456",
                                          "Test City"));
    model.setChildren(new ArrayList<>());
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  @Test
  public void testCheckFailed01() {
    Person model = new Person("Flintstones",
                              "Fred",
                              new Address("Test Avenue",
                                          "123456",
                                          "Test City"));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  @Test
  public void testValidateFailed01() {
    Person model = new Person("Flintstones",
                              "Fred",
                              new Address("Test Avenue",
                                          "123456",
                                          "Test City"));
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(result.isValid());
  }
  //
  //  @Test
  //  public void testCheckFail01() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              null);
  //
  //    MalioValidationException thrown = assertThrows(MalioValidationException.class,
  //                                                   () -> PersonMalioValidator.INSTANCE.check(model));
  //    //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  //  }
  //
  //  @Test
  //  public void testCheckFail02() {
  //    Person model = new Person("Fred",
  //                              "Flintstones",
  //                              new Address(null,
  //                                          "123456",
  //                                          "Test City"));
  //
  //    MalioValidationException thrown = assertThrows(MalioValidationException.class,
  //                                                   () -> PersonMalioValidator.INSTANCE.check(model));
  //    //    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  //  }
  //
  //  @Test
  //  public void testValidateFail01() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              null);
  //
  //    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
  //
  //    assertFalse(result.isValid());
  //    assertEquals(1,
  //                 result.getMessages()
  //                       .size());
  //
  //    ErrorMessage errorMessage01 = result.getMessages()
  //                                        .get(0);
  //    assertEquals("com.github.nalukit.malio.test.model.notnull01.Person",
  //                 errorMessage01.getClassname());
  //    assertEquals("Person",
  //                 errorMessage01.getSimpleClassname());
  //    assertEquals("address",
  //                 errorMessage01.getField());
  //    assertEquals("n/a",
  //                 errorMessage01.getMessage());
  //  }
  //
  //  @Test
  //  public void testValidateFail02() {
  //    Person model = new Person("Fred",
  //                              "Flintstones",
  //                              new Address(null,
  //                                          "123456",
  //                                          "Test City"));
  //
  //    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
  //
  //    assertFalse(result.isValid());
  //    assertEquals(1,
  //                 result.getMessages()
  //                       .size());
  //
  //    ErrorMessage errorMessage01 = result.getMessages()
  //                                        .get(0);
  //    assertEquals("com.github.nalukit.malio.test.model.notnull01.Address",
  //                 errorMessage01.getClassname());
  //    assertEquals("Address",
  //                 errorMessage01.getSimpleClassname());
  //    assertEquals("street",
  //                 errorMessage01.getField());
  //    assertEquals("n/a",
  //                 errorMessage01.getMessage());
  //  }

}
