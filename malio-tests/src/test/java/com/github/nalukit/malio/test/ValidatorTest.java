package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.Address;
import com.github.nalukit.malio.test.model.Person;
import com.github.nalukit.malio.test.model.PersonMalioValidator;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ValidatorTest {


  @Test
  public void testCheckOk() {
    Person model = new Person("Fred", "Flientstone", new Address("Test Avenue 21", "123456", "Test City"));

    try {
      PersonMalioValidator.INSTANCE.check(model);
    } catch (MalioValidationException e) {
      fail();
    }
  }

  @Test
  public void testValidateOk() {
    Person model = new Person("Fred", "Flientstone", new Address("Test Avenue 21", "123456", "Test City"));
    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  @Test
  public void testCheckFail01() {
    Person model = new Person(null, "Flientstone", new Address("Test Avenue 21", "123456", "Test City"));

    MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonMalioValidator.INSTANCE.check(model));
//    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  }

  @Test
  public void testValidateFail01() {
    Person model = new Person(null, "Flientstone", new Address("Test Avenue 21", "123456", "Test City"));

    ValidationResult result = PersonMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(1,
                 result.getMessages()
                       .size());
    ErrorMessage errorMessage = result.getMessages()
                                     .get(0);
    assertEquals("com.github.nalukit.malio.test.model.Person",
                 errorMessage .getClassname());
    assertEquals("Person",
                 errorMessage  .getSimpleClassname());
    assertEquals("name",
                 errorMessage   .getField());
    assertEquals("n/a",
                 errorMessage    .getMessage());
  }



}
