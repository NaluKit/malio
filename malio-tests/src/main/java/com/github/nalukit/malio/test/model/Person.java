package com.github.nalukit.malio.test.model;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.NotNull;
import com.github.nalukit.malio.shared.annotation.field.UseMalioValidator;

@MalioValidator
public class Person {

  @NotNull
  private String name;

  @NotNull
  private String firstName;

  @NotNull
  @UseMalioValidator
  private Address address;

  public Person() {
  }

  public Person(String name,
                String firstName,
                Address address) {
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

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}
