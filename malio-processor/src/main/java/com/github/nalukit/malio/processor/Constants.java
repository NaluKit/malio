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
package com.github.nalukit.malio.processor;

public class Constants {
  public final static String MALIO_CONSTRAINT_REGEXP_IMPL_NAME    = "MalioConstraintRegexp";
  public final static String MALIO_CONSTRAINT_EMAIL_IMPL_NAME     = "MalioConstraintEmail";
  public final static String MALIO_CONSTRAINT_UUID_IMPL_NAME      = "MalioConstraintUuid";
  public final static String MALIO_CONSTRAINT_WHITELIST_IMPL_NAME = "MalioConstraintWhitelist";
  public final static String MALIO_CONSTRAINT_BLACKLIST_IMPL_NAME = "MalioConstraintBlacklist";
  public final static String MALIO_CONSTRAINT_MINVALUE_IMPL_NAME  = "MalioConstraintMinValue";
  public final static String MALIO_CONSTRAINT_MAXVALUE_IMPL_NAME  = "MalioConstraintMaxValue";

  public final static String MALIO_CONSTRAINT_MAXLENGTH_IMPL_NAME       = "MalioConstraintMaxLength";
  public final static String MALIO_CONSTRAINT_MINLENGTH_IMPL_NAME       = "MalioConstraintMinLength";
  public final static String MALIO_CONSTRAINT_NOT_NULL_IMPL_NAME        = "MalioConstraintNotNull";
  public final static String MALIO_VALIDATOR_IMPL_NAME                  = "MalioValidator";
  public static final String MALIO_CONSTRAINT_MAXDECIMALVALUE_IMPL_NAME = "MalioConstraintMaxDecimal";
  public static final String MALIO_CONSTRAINT_MINDECIMALVALUE_IMPL_NAME = "MalioConstraintMinDecimal";
  public static final String MALIO_CONSTRAINT_NOTBLANK_IMPL_NAME        = "MalioConstraintNotBlank";
  public static final String MALIO_CONSTRAINT_NOTEMPTY_IMPL_NAME        = "MalioConstraintNotEmpty";
  public static final String MALIO_CONSTRAINT_SIZE_IMPL_NAME            = "MalioConstraintSize";
  public static final String MALIO_CONSTRAINT_ARRAYSIZE_IMPL_NAME            = "MalioConstraintArraySize";
  public static final String MALIO_CONSTRAINT_NOTZERO_IMPL_NAME            = "MalioConstraintNotZero";
}
