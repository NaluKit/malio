package com.github.nalukit.malio.test.model.regexp01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.Blacklist;
import com.github.nalukit.malio.shared.annotation.field.Regexp;

@MalioValidator
public class Address {

  @Regexp(regexp = ".+ Street")
  private String street;
  @Regexp(regexp = "\\d{5}")
  private String zip;

  @Regexp(regexp = ".+ City")
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
