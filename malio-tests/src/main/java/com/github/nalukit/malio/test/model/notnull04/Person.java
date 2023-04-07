package com.github.nalukit.malio.test.model.notnull04;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.NotNull;
import com.github.nalukit.malio.test.model.notnull04.helper.AbstractPerson;

@MalioValidator
public class Person
    extends AbstractPerson {

  @NotNull
  private String firstName;

  public Person() {
  }

  public Person(String name,
                String firstName) {
    super(name);
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
}
