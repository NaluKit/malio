package com.github.nalukit.malio.test.model.notblank01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.NotBlank;
import com.github.nalukit.malio.shared.annotation.field.NotNull;
import com.github.nalukit.malio.test.model.notnull01.Address;

@MalioValidator
public class Person {

  @NotBlank
  private String name;

  @NotBlank
  private String firstName;


  public Person() {
  }

  public Person(String name,
                String firstName) {
    this.name      = name;
    this.firstName = firstName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

}
