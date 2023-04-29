package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.subvalidator03.Address;
import com.github.nalukit.malio.test.model.subvalidator03.Person;
import com.github.nalukit.malio.test.model.subvalidator03.PersonMalioValidator;
import com.google.gwt.junit.client.GWTTestCase;
import org.junit.Test;

import java.util.ArrayList;

public class SubValidator03Test
    extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "com.github.nalukit.malio.MalioGwt2Test";
  }

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
  public void testCheckOk02() {
    Person model = new Person("Flintstones",
                              "Fred",
                              new Address("Test Avenue",
                                          "123456",
                                          "Test City"));
    model.setChildren(new ArrayList<>());
    model.getChildren()
         .add(new Person("Flintstones",
                         "Selma",
                         new Address("Test Avenue",
                                     "123456",
                                     "Test City"),
                         new ArrayList<>()));
    try {
      PersonMalioValidator.INSTANCE.check(model);
    } catch (MalioValidationException e) {
      fail();
    }
  }

  @Test
  public void testValidateOk02() {
    Person model = new Person("Flintstones",
                              "Fred",
                              new Address("Test Avenue",
                                          "123456",
                                          "Test City"));
    model.setChildren(new ArrayList<>());
    model.getChildren()
         .add(new Person("Flintstones",
                         "Selma",
                         new Address("Test Avenue",
                                     "123456",
                                     "Test City"),
                         new ArrayList<>()));
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

  @Test
  public void testCheckFailed02() {
    Person model = new Person("Flintstones",
                              "Fred",
                              new Address("Test Avenue",
                                          "123456",
                                          "Test City"));
    model.setChildren(new ArrayList<>());
    model.getChildren()
         .add(new Person(null,
                         "Selma",
                         new Address("Test Avenue",
                                     "123456",
                                     "Test City")));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  @Test
  public void testValidateFailed02() {
    Person model = new Person("Flintstones",
                              "Fred",
                              new Address("Test Avenue",
                                          "123456",
                                          "Test City"));
    model.setChildren(new ArrayList<>());
    model.getChildren()
         .add(new Person(null,
                         "Selma",
                         new Address("Test Avenue",
                                     "123456",
                                     "Test City")));
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(result.isValid());
  }

  @Test
  public void testCheckFailed03() {
    Person model = new Person("Flintstones",
                              "Fred",
                              new Address("Test Avenue",
                                          "123456",
                                          "Test City"));
    model.setChildren(new ArrayList<>());
    model.getChildren()
         .add(new Person("Flintstones",
                         "Selma",
                         new Address("Test Avenue",
                                     null,
                                     "Test City")));
    try {
      PersonMalioValidator.INSTANCE.check(model);
      fail();
    } catch (MalioValidationException e) {
    }
  }

  @Test
  public void testValidateFailed03() {
    Person model = new Person("Flintstones",
                              "Fred",
                              new Address("Test Avenue",
                                          "123456",
                                          "Test City"));
    model.setChildren(new ArrayList<>());
    model.getChildren()
         .add(new Person("Flintstones",
                         "Selma",
                         new Address("Test Avenue",
                                     null,
                                     "Test City")));
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertFalse(result.isValid());
  }

}
