package com.github.nalukit.malio.test.model.notempty01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.NotBlank;
import com.github.nalukit.malio.shared.annotation.field.NotEmpty;

import java.util.Arrays;
import java.util.List;

@MalioValidator
public class Person {

  @NotEmpty
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
