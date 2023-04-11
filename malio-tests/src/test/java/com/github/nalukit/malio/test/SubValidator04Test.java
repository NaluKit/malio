package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.test.model.subvalidator04.Person;
import com.github.nalukit.malio.test.model.subvalidator04.PersonMalioValidator;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class SubValidator04Test {

  @Test
  public void testCheckOk01() {
    Person modelParent = new Person("Simpsons",
                                    "Homer");
    Person modelChild = new Person("Simpsons",
                                   "Bart");
    modelChild.setParent(modelParent);
    modelParent.setChild(modelChild);
    try {
      PersonMalioValidator.INSTANCE.check(modelParent);
    } catch (MalioValidationException e) {
      fail();
    }
  }

  //  @Test
  //  public void testValidateOk01() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    model.setChildren(new ArrayList<>());
  //    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
  //    assertTrue(result.isValid());
  //  }
  //
  //  @Test
  //  public void testCheckOk02() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    model.setChildren(new ArrayList<>());
  //    model.getChildren()
  //         .add(new Person("Flintstones",
  //                         "Selma",
  //                         new Address("Test Avenue",
  //                                     "123456",
  //                                     "Test City"),
  //                         new ArrayList<>()));
  //    try {
  //      PersonMalioValidator.INSTANCE.check(model);
  //    } catch (MalioValidationException e) {
  //      fail();
  //    }
  //  }
  //
  //  @Test
  //  public void testValidateOk02() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    model.setChildren(new ArrayList<>());
  //    model.getChildren()
  //         .add(new Person("Flintstones",
  //                         "Selma",
  //                         new Address("Test Avenue",
  //                                     "123456",
  //                                     "Test City"),
  //                         new ArrayList<>()));
  //    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
  //    assertTrue(result.isValid());
  //  }
  //
  //  @Test
  //  public void testCheckFailed01() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    try {
  //      PersonMalioValidator.INSTANCE.check(model);
  //      fail();
  //    } catch (MalioValidationException e) {
  //    }
  //  }
  //
  //  @Test
  //  public void testValidateFailed01() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
  //    assertFalse(result.isValid());
  //  }
  //
  //  @Test
  //  public void testCheckFailed02() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    model.setChildren(new ArrayList<>());
  //    model.getChildren()
  //         .add(new Person(null,
  //                         "Selma",
  //                         new Address("Test Avenue",
  //                                     "123456",
  //                                     "Test City")));
  //    try {
  //      PersonMalioValidator.INSTANCE.check(model);
  //      fail();
  //    } catch (MalioValidationException e) {
  //    }
  //  }
  //
  //  @Test
  //  public void testValidateFailed02() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    model.setChildren(new ArrayList<>());
  //    model.getChildren()
  //         .add(new Person(null,
  //                         "Selma",
  //                         new Address("Test Avenue",
  //                                     "123456",
  //                                     "Test City")));
  //    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
  //    assertFalse(result.isValid());
  //  }
  //
  //  @Test
  //  public void testCheckFailed03() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    model.setChildren(new ArrayList<>());
  //    model.getChildren()
  //         .add(new Person("Flintstones",
  //                         "Selma",
  //                         new Address("Test Avenue",
  //                                     null,
  //                                     "Test City")));
  //    try {
  //      PersonMalioValidator.INSTANCE.check(model);
  //      fail();
  //    } catch (MalioValidationException e) {
  //    }
  //  }
  //
  //  @Test
  //  public void testValidateFailed03() {
  //    Person model = new Person("Flintstones",
  //                              "Fred",
  //                              new Address("Test Avenue",
  //                                          "123456",
  //                                          "Test City"));
  //    model.setChildren(new ArrayList<>());
  //    model.getChildren()
  //         .add(new Person("Flintstones",
  //                         "Selma",
  //                         new Address("Test Avenue",
  //                                     null,
  //                                     "Test City")));
  //    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
  //    assertFalse(result.isValid());
  //  }

}
