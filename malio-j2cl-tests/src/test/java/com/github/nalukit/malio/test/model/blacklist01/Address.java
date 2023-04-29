package com.github.nalukit.malio.test.model.blacklist01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.Blacklist;

@MalioValidator
public class Address {

  @Blacklist({"Str", "Secret"})
  private String street;
  @Blacklist("12345")
  private String zip;

  private String city;

  public Address() {
  }

  public Address(String street,
                 String zip,
                 String city) {
    this.street = street;
    this.zip    = zip;
    this.city   = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }
}
