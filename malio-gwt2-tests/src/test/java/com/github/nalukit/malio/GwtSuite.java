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
package com.github.nalukit.malio;

import com.github.nalukit.malio.test.*;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;

public class GwtSuite {

  public static Test suite() {
    GWTTestSuite suite = new GWTTestSuite("Test suite for malio");

    suite.addTestSuite(ArrayItemMaxLength01Test.class);
    suite.addTestSuite(ArrayItemMinLength01Test.class);
    suite.addTestSuite(ArrayItemNotBlank01Test.class);
    suite.addTestSuite(ArrayItemNotNull01Test.class);
    suite.addTestSuite(ArrayItemNotNull02Test.class);
    suite.addTestSuite(CheckAndValidate01Test.class);
    suite.addTestSuite(CheckOnly01Test.class);
    suite.addTestSuite(CollectionItemMaxLength01Test.class);
    suite.addTestSuite(CollectionItemMinLength01Test.class);
    suite.addTestSuite(CollectionItemNotBlank01Test.class);
    suite.addTestSuite(CollectionItemNotNull01Test.class);
    suite.addTestSuite(SubValidator01Test.class);
    suite.addTestSuite(SubValidator02Test.class);
    suite.addTestSuite(SubValidator03Test.class);
    suite.addTestSuite(ValidateItem01Test.class);
    suite.addTestSuite(ValidateItem02Test.class);
    suite.addTestSuite(ValidateItem03Test.class);
    suite.addTestSuite(ValidateOnly01Test.class);
    suite.addTestSuite(ValidatorArraySize01Test.class);
    suite.addTestSuite(ValidatorBlacklist01Test.class);
    suite.addTestSuite(ValidatorDecimalMax01Test.class);
    suite.addTestSuite(ValidatorDecimalMin01Test.class);
    suite.addTestSuite(ValidatorEmail01Test.class);
    suite.addTestSuite(ValidatorMax01Test.class);
    suite.addTestSuite(ValidatorMaxLength01Test.class);
    suite.addTestSuite(ValidatorMin01Test.class);
    suite.addTestSuite(ValidatorMinLength01Test.class);
    suite.addTestSuite(ValidatorMixedUp01Test.class);
    suite.addTestSuite(ValidatorMixedUp02Test.class);
    suite.addTestSuite(ValidatorNotBlank01Test.class);
    suite.addTestSuite(ValidatorNotEmpty01Test.class);
    suite.addTestSuite(ValidatorNotNull01Test.class);
    suite.addTestSuite(ValidatorNotNull02Test.class);
    suite.addTestSuite(ValidatorNotNull03Test.class);
    suite.addTestSuite(ValidatorNotNull04Test.class);
    suite.addTestSuite(ValidatorNotNull05Test.class);
    suite.addTestSuite(ValidatorNotNull06Test.class);
    suite.addTestSuite(ValidatorNotNull07Test.class);
    suite.addTestSuite(ValidatorNotZeroTest.class);
    suite.addTestSuite(ValidatorRegexpTest.class);
    suite.addTestSuite(ValidatorSize01Test.class);
    suite.addTestSuite(ValidatorUuid01Test.class);
    suite.addTestSuite(ValidatorWhitelistTest.class);

    return suite;
  }
}
