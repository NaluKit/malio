package com.github.nalukit.malio.shared.model;

import com.github.nalukit.malio.shared.util.GUID;

public class ErrorMessage {

  private String id;
  private String message;
  private String classname;
  private String simpleClassname;
  private String field;

  public ErrorMessage() {
    this.id = GUID.get();
  }

  public ErrorMessage(String message,
                      String classname,
                      String simpleClassname,
                      String field) {
    this.message         = message;
    this.classname       = classname;
    this.simpleClassname = simpleClassname;
    this.field           = field;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getClassname() {
    return classname;
  }

  public void setClassname(String classname) {
    this.classname = classname;
  }

  public String getSimpleClassname() {
    return simpleClassname;
  }

  public void setSimpleClassname(String simpleClassname) {
    this.simpleClassname = simpleClassname;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }
}
