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
package com.github.nalukit.malio.processor.model;

import javax.lang.model.element.TypeElement;

public class ValidatorModel {

  private String      packageName;
  private String      simpleClassName;
  private String      fieldName;
  private String        postFix;
  private ComponentType componentType;
  private TypeElement   genericTypeElement01;
  private TypeElement genericTypeElement02;

  public ValidatorModel(String packageName,
                        String simpleClassName,
                        String fieldName,
                        String postFix,
                        ComponentType componentType) {
    this.packageName     = packageName;
    this.simpleClassName = simpleClassName;
    this.fieldName       = fieldName;
    this.postFix         = postFix;
    this.componentType   = componentType;
  }

  public ValidatorModel(String packageName,
                        String simpleClassName,
                        String fieldName,
                        String postFix,
                        ComponentType componentType,
                        TypeElement genericTypeElement01) {
    this(packageName,
         simpleClassName,
         fieldName,
         postFix,
         componentType);
    this.genericTypeElement01 = genericTypeElement01;
  }

  public ValidatorModel(String packageName,
                        String simpleClassName,
                        String fieldName,
                        String postFix,
                        ComponentType componentType,
                        TypeElement genericTypeElement01,
                        TypeElement genericTypeElement02) {
    this(packageName,
         simpleClassName,
         fieldName,
         postFix,
         componentType,
         genericTypeElement01);
    this.genericTypeElement02 = genericTypeElement02;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getSimpleClassName() {
    return simpleClassName;
  }

  public String getFieldName() {
    return fieldName;
  }

  public String getPostFix() {
    return postFix;
  }

  public ComponentType getType() {
    return componentType;
  }

  public TypeElement getGenericTypeElement01() {
    return genericTypeElement01;
  }

  public TypeElement getGenericTypeElement02() {
    return genericTypeElement02;
  }

  public enum ComponentType {
    ARRAY,
    COLLECTION,
    NATIVE
  }
}
