package com.github.nalukit.malio.test.model.subvalidator02;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.NotNull;
import com.github.nalukit.malio.test.model.notnull01.Address;

import java.util.List;

@MalioValidator
public class Person {

  @NotNull private String name;

  @NotNull private String firstName;

  private List<Person> children;

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

  public List<Person> getChildren() {
    return children;
  }

  public void setChildren(List<Person> children) {
    this.children = children;
  }
}
