package com.github.nalukit.malio.test;

import com.github.nalukit.malio.shared.MalioValidationException;
import com.github.nalukit.malio.shared.model.ErrorMessage;
import com.github.nalukit.malio.shared.model.ValidationResult;
import com.github.nalukit.malio.test.model.AddressForNotNull;
import com.github.nalukit.malio.test.model.PersonNotNull;
import com.github.nalukit.malio.test.model.PersonNotNullMalioValidator;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ValidatorNotNullTest {


  @Test
  public void testCheckNotNullOk() {
    PersonNotNull model = new PersonNotNull("Fred", "Flientstone", new AddressForNotNull("Test Avenue 21", "123456", "Test City"));

    try {
      PersonNotNullMalioValidator.INSTANCE.check(model);
    } catch (MalioValidationException e) {
      fail();
    }
  }

  @Test
  public void testValidateNotNullOk() {
    PersonNotNull    model  = new PersonNotNull("Fred", "Flientstone", new AddressForNotNull("Test Avenue 21", "123456", "Test City"));
    ValidationResult result = PersonNotNullMalioValidator.INSTANCE.validate(model);
    assertTrue(result.isValid());
  }

  @Test
  public void testCheckNotNullFail01() {
    PersonNotNull model = new PersonNotNull(null, "Flientstone", new AddressForNotNull("Test Avenue 21", "123456", "Test City"));

    MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonNotNullMalioValidator.INSTANCE.check(model));
//    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  }

  @Test
  public void testCheckNotNullFail02() {
    PersonNotNull model = new PersonNotNull(null, null, new AddressForNotNull("Test Avenue 21", "123456", "Test City"));

    MalioValidationException thrown = assertThrows(MalioValidationException.class, () -> PersonNotNullMalioValidator.INSTANCE.check(model));
//    assertTrue(thrown.getMessage().contentEquals("asd sad "));
  }

  @Test
  public void testValidateNotNullFail01() {
    PersonNotNull model = new PersonNotNull(null, "Flientstone", new AddressForNotNull("Test Avenue 21", "123456", "Test City"));

    ValidationResult result = PersonNotNullMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(1,
                 result.getMessages()
                       .size());
    ErrorMessage errorMessage = result.getMessages()
                                     .get(0);
    assertEquals("com.github.nalukit.malio.test.model.PersonNotNull",
                 errorMessage .getClassname());
    assertEquals("PersonNotNull",
                 errorMessage  .getSimpleClassname());
    assertEquals("name",
                 errorMessage   .getField());
    assertEquals("n/a",
                 errorMessage    .getMessage());
  }

  @Test
  public void testValidateNotNullFail02() {
    PersonNotNull model = new PersonNotNull(null, null, new AddressForNotNull("Test Avenue 21", "123456", "Test City"));

    ValidationResult result = PersonNotNullMalioValidator.INSTANCE.validate(model);

    assertFalse(result.isValid());
    assertEquals(2,
                 result.getMessages()
                       .size());

    ErrorMessage errorMessage01 = result.getMessages()
                                     .get(0);
    assertEquals("com.github.nalukit.malio.test.model.PersonNotNull",
                 errorMessage01 .getClassname());
    assertEquals("PersonNotNull",
                 errorMessage01  .getSimpleClassname());
    assertEquals("name",
                 errorMessage01   .getField());
    assertEquals("n/a",
                 errorMessage01    .getMessage());

    ErrorMessage errorMessage02 = result.getMessages()
                                     .get(1);
    assertEquals("com.github.nalukit.malio.test.model.PersonNotNull",
                 errorMessage02 .getClassname());
    assertEquals("PersonNotNull",
                 errorMessage02  .getSimpleClassname());
    assertEquals("firstName",
                 errorMessage02   .getField());
    assertEquals("n/a",
                 errorMessage02    .getMessage());
  }



}
