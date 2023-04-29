package com.github.nalukit.malio.test.model.size;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.Size;

import java.util.List;

@MalioValidator
public class Person {

  @Size(min = 2, max = 4)
  private List<String> pocket;
  public Person() {
  }

  public Person(List<String> things) {
    this.pocket = things;
  }

  public List<String> getPocket() {
    return pocket;
  }

  public void setPocket(List<String> pocket) {
    this.pocket = pocket;
  }
}
