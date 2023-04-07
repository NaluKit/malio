package com.github.nalukit.malio.test.model.notnull02;

import com.github.nalukit.malio.shared.annotation.field.NotNull;

public class AbstractPerson {

  @NotNull
  private String name;

  public AbstractPerson() {
  }

  public AbstractPerson(String name) {
    this.name      = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
