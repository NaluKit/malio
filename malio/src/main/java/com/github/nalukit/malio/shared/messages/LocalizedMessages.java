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

import com.github.nalukit.malio.shared.messages.locales.MessagesEN;

import java.math.BigDecimal;

public class LocalizedMessages
    implements IMessages {

  public final static LocalizedMessages INSTANCE = new LocalizedMessages();

  private IMessages messages = new MessagesEN();

  private LocalizedMessages() {
    // Nothing to do here :)
  }

  public IMessages getMessages() {
    return this.messages;
  }

  public void setMessages(IMessages messages) {
    this.messages = messages;
  }

  @Override
  public String getBlacklistMessage(String value) {
    return format(messages.getBlacklistMessage(value),
                  value);
  }

  @Override
  public String getEmailMessage() {
    return messages.getEmailMessage();
  }

  @Override
  public String getMaxDecimalValueMessage(BigDecimal max) {
    return format(messages.getMaxDecimalValueMessage(max),
                  max.toString());
  }

  @Override
  public String getMaxLengthMessage(int length) {
    return format(messages.getMaxLengthMessage(length),
                  String.valueOf(length));
  }

  @Override
  public String getMaxValueMessage(Long max) {
    return format(messages.getMaxValueMessage(max),
                  max.toString());
  }

  @Override
  public String getMinDecimalValueMessage(BigDecimal min) {
    return format(messages.getMinDecimalValueMessage(min),
                  min.toString());
  }

  @Override
  public String getMinLengthMessage(int min) {
    return format(messages.getMinLengthMessage(min),
                  String.valueOf(min));
  }

  @Override
  public String getMinValueMessage(Long min) {
    return format(messages.getMinValueMessage(min),
                  min.toString());
  }

  @Override
  public String getNotBlankMessage() {
    return messages.getNotBlankMessage();
  }

  @Override
  public String getNotEmptyMessage() {
    return messages.getNotEmptyMessage();
  }

  @Override
  public String getNotNullMessage() {
    return messages.getNotNullMessage();
  }

  @Override
  public String getRegexpMessage(String value) {
    return format(messages.getRegexpMessage(value),
                  value);
  }

  @Override
  public String getSizeMessage(int min,
                               int max) {
    return format(messages.getSizeMessage(min,
                                          max),
                  String.valueOf(min),
                  String.valueOf(max));
  }

  @Override
  public String getArraySizeMessage(int min, int max) {
    return format(messages.getArraySizeMessage(min, max),
            String.valueOf(min),
            String.valueOf(max));
  }

  @Override
  public String getUuidMessage() {
    return messages.getUuidMessage();
  }

  @Override
  public String getWhitelistMessage(String value) {
    return format(messages.getWhitelistMessage(value),
                  value);
  }

  @Override
  public String getNotZeroMessage() {
    return messages.getNotZeroMessage();
  }

  private String format(String message,
                        String... args) {
    for (int i = 0; i < args.length; i++) {
      String value = args[i];
      if (value == null) {
        value = "null";
      }
      message = message.replace("{" + i + "}",
                                value);
    }

    return message;
  }

}
