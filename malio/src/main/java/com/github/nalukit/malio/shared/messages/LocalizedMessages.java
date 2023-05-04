package com.github.nalukit.malio.shared.messages;

import com.github.nalukit.malio.shared.messages.locales.MessagesEN;

import java.math.BigDecimal;

public class LocalizedMessages implements IMessages {

    public final static LocalizedMessages INSTANCE = new LocalizedMessages();

    private IMessages messages = new MessagesEN();

    private LocalizedMessages() {
      // Nothing to do here :)
    }

    public void setMessages(IMessages messages) {
        this.messages = messages;
    }

    @Override
    public String getBlacklistMessage(String value) {
        return format(messages.getBlacklistMessage(value), value);
    }

    @Override
    public String getEmailMessage() {
        return messages.getEmailMessage();
    }

    @Override
    public String getMaxDecimalValueMessage(BigDecimal max) {
        return format(messages.getMaxDecimalValueMessage(max), max.toString());
    }

    @Override
    public String getMaxLengthMessage(int length) {
        return format(messages.getMaxLengthMessage(length), String.valueOf(length));
    }

    @Override
    public String getMaxValueMessage(Long max) {
        return format(messages.getMaxValueMessage(max), max.toString());
    }

    @Override
    public String getMinDecimalValueMessage(BigDecimal min) {
        return format(messages.getMinDecimalValueMessage(min), min.toString());
    }

    @Override
    public String getMinLengthMessage(int min) {
        return format(messages.getMinLengthMessage(min), String.valueOf(min));
    }

    @Override
    public String getMinValueMessage(Long min) {
        return format(messages.getMinValueMessage(min), min.toString());
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
        return format(messages.getRegexpMessage(value), value);
    }

    @Override
    public String getSizeMessage(int min, int max) {
        return format(messages.getSizeMessage(min, max), String.valueOf(min), String.valueOf(max));
    }

    @Override
    public String getWhitelistMessage(String value) {
        return format(messages.getWhitelistMessage(value), value);
    }


    private String format(String message, String ... args) {
        for (int i = 0; i < args.length; i++) {
            String value = args[i];
            if (value == null) {
                value = "null";
            }
            message = message.replace("{" + i + "}", value);
        }

        return message;
    }

}
