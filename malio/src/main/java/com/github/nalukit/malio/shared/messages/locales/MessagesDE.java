package com.github.nalukit.malio.shared.messages.locales;

import com.github.nalukit.malio.shared.messages.IMessages;

import java.math.BigDecimal;

public class MessagesDE implements IMessages {

    @Override
    public String getBlacklistMessage(String value) {
        return "String '{0}' ist nicht erlaubt!";
    }

    @Override
    public String getEmailMessage() {
        return "String repräsentiert keine E-Mail Adresse!";
    }

    @Override
    public String getMaxDecimalValueMessage(BigDecimal max) {
        return "Wert darf nicht größer als {0} sein.";
    }

    @Override
    public String getMaxLengthMessage(int length) {
        return "Wert darf nicht länger als {0} sein.";
    }

    @Override
    public String getMaxValueMessage(Long max) {
        return "Wert darf nicht größer als {0} sein.";
    }

    @Override
    public String getMinDecimalValueMessage(BigDecimal min) {
        return "Wert darf nicht kleiner als {0} sein.";
    }

    @Override
    public String getMinLengthMessage(int min) {
        return "Wert darf nicht kürzer als {0} sein.";
    }

    @Override
    public String getMinValueMessage(Long min) {
        return "Wert darf nicht kleiner als {0} sein.";
    }

    @Override
    public String getNotBlankMessage() {
        return "String darf nicht leer sein.";
    }

    @Override
    public String getNotEmptyMessage() {
        return "Collection darf nicht leer sein!";
    }

    @Override
    public String getNotNullMessage() {
        return "Objekt darf nicht null sein!";
    }

    @Override
    public String getRegexpMessage(String value) {
        return "String '{0}' ist nicht erlaubt!";
    }

    @Override
    public String getSizeMessage(int min, int max) {
        return "Collection Länge muss zwischen {0} und {1} sein!";
    }

    @Override
    public String getWhitelistMessage(String value) {
        return "String '{0}' ist nicht erlaubt!";
    }
}
