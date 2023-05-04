package com.github.nalukit.malio.shared.messages.locales;

import com.github.nalukit.malio.shared.messages.IMessages;

import java.math.BigDecimal;

public class MessagesEN implements IMessages {

    @Override
    public String getBlacklistMessage(String value) {
        return "String '{0}' is not allowed!";
    }

    @Override
    public String getEmailMessage() {
        return "String does not represent an email address!";
    }

    @Override
    public String getMaxDecimalValueMessage(BigDecimal max) {
        return "Value must not be greater than {0}.";
    }

    @Override
    public String getMaxLengthMessage(int length) {
        return "Value must not be longer than {0}.";
    }

    @Override
    public String getMaxValueMessage(Long max) {
        return "Value must not be greater than {0}.";
    }

    @Override
    public String getMinDecimalValueMessage(BigDecimal min) {
        return "Value must not be smaller than {0}.";
    }

    @Override
    public String getMinLengthMessage(int min) {
        return "Value must not be shorter than {0}.";
    }

    @Override
    public String getMinValueMessage(Long min) {
        return "Value must not be smaller than {0}.";
    }

    @Override
    public String getNotBlankMessage() {
        return "String must not be empty.";
    }

    @Override
    public String getNotEmptyMessage() {
        return "Collection must not be empty!";
    }

    @Override
    public String getNotNullMessage() {
        return "Object must not be null!";
    }

    @Override
    public String getRegexpMessage(String value) {
        return "String '{0}' is not allowed!";
    }

    @Override
    public String getSizeMessage(int min, int max) {
        return "Collection size must be between {0} and {1}!";
    }

    @Override
    public String getWhitelistMessage(String value) {
        return "String '{0}' is not allowed!";
    }
}
