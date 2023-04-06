package com.github.nalukit.malio.example.shared.dto;

import com.github.nalukit.malio.example.shared.model.Address;
import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.NotNull;
import com.github.nalukit.malio.shared.annotation.field.UseMalioValidator;

@MalioValidator
public class PersonDto {

  @NotNull
  private String name;

  @NotNull
  private String firstName;

  @NotNull
  @UseMalioValidator
  private Address address;

  public PersonDto() {
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
