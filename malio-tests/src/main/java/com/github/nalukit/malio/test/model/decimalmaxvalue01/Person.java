package com.github.nalukit.malio.test.model.decimalmaxvalue01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.DecimalMaxValue;

import java.math.BigDecimal;

@MalioValidator
public class Person {

    @DecimalMaxValue("0.5")
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
