package com.github.nalukit.malio.test.model.decimalminvalue01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.DecimalMinValue;

import java.math.BigDecimal;

@MalioValidator
public class Person {

    @DecimalMinValue("0.1")
    private BigDecimal taxRate;


    public Person() {
    }

    public Person(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
