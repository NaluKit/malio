package com.github.nalukit.malio.test.model.whitelist01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.Whitelist;

@MalioValidator
public class Address {

  @Whitelist({"My Street"})
  private String street;
  @Whitelist("54321")
  private String zip;

  @Whitelist({"Home Town", "My Town"})
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
