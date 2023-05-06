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
package com.github.nalukit.malio.shared.messages;

import java.math.BigDecimal;

public interface IMessages {

  String getBlacklistMessage(String value);

  String getEmailMessage();

  String getMaxDecimalValueMessage(BigDecimal max);

  String getMaxLengthMessage(int length);

  String getMaxValueMessage(Long max);

  String getMinDecimalValueMessage(BigDecimal min);

  String getMinLengthMessage(int min);

  String getMinValueMessage(Long min);

  String getNotBlankMessage();

  String getNotEmptyMessage();

  String getNotNullMessage();

  String getRegexpMessage(String value);

  String getSizeMessage(int min,
                        int max);

  String getWhitelistMessage(String value);
}
