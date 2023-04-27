/*
 * Copyright © 2020 ${name}
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
    GWTTestSuite suite = new GWTTestSuite("Test suite for lib");
    suite.addTestSuite(ValidatorNotNull01Test.class);
    suite.addTestSuite(ValidatorNotNull02Test.class);
    suite.addTestSuite(ValidatorNotNull03Test.class);
    suite.addTestSuite(ValidatorNotNull04Test.class);
    suite.addTestSuite(ValidatorNotNull05Test.class);
    return suite;
  }
}
