package com.github.nalukit.malio.test.model.notnull02;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.NotNull;

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
