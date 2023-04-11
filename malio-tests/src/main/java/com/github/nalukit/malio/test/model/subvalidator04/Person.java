package com.github.nalukit.malio.test.model.subvalidator04;

import com.github.nalukit.malio.shared.annotation.MalioIgnore;
import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.NotNull;

@MalioValidator
public class Person {

  @NotNull private String name;
  @NotNull private String firstName;

  @MalioIgnore
  private Person parent;
  private Person child;

  public Person() {
  }

  public Person(String name,
                String firstName) {
    this();
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

  public Person getParent() {
    return parent;
  }

  public void setParent(Person parent) {
    this.parent = parent;
  }

  public Person getChild() {
    return child;
  }

  public void setChild(Person child) {
    this.child = child;
  }
}