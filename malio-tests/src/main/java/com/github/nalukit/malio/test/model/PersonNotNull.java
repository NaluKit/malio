package com.github.nalukit.malio.test.model;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.NotNull;
import com.github.nalukit.malio.shared.annotation.field.UseMalioValidator;

@MalioValidator
public class PersonNotNull {

  @NotNull
  private String name;

  @NotNull
  private String firstName;

  @NotNull
  @UseMalioValidator
  private AddressForNotNull address;

  public PersonNotNull() {
  }

  public PersonNotNull(String name,
                       String firstName,
                       AddressForNotNull address) {
    this.name      = name;
    this.firstName = firstName;
    this.address   = address;
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

  public AddressForNotNull getAddress() {
    return address;
  }

  public void setAddress(AddressForNotNull address) {
    this.address = address;
  }
}
