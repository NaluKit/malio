/*
 * Copyright © 2023 Frank Hossfeld, Philipp Kohl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.nalukit.malio.shared.model;

import io.github.nalukit.malio.shared.util.GUID;

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
