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

    String getSizeMessage(int min, int max);

    String getWhitelistMessage(String value);
}
