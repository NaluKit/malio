package com.github.nalukit.malio.test.dto;

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
  private AddressDto address;

  public PersonDto() {
  }

  public PersonDto(String name,
                   String firstName,
                   AddressDto address) {
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

  public AddressDto getAddress() {
    return address;
  }

  public void setAddress(AddressDto address) {
    this.address = address;
  }
}
