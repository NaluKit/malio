package com.github.nalukit.malio.test.model.minvalue01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.DecimalMinValue;
import com.github.nalukit.malio.shared.annotation.field.MaxValue;
import com.github.nalukit.malio.shared.annotation.field.MinValue;

import java.math.BigDecimal;

@MalioValidator
public class Person {

    private String name;

    @MinValue(18)
    private int age;

//    @MinValue(0.1)
    private float taxRate;

    @MinValue(5)
    private Integer complexTypeTest;

    @DecimalMinValue("0")
    private BigDecimal bankAccount;

    public Person() {
    }

    public Person(String name,
                  int age,
                  float taxRate,
                  BigDecimal bankAccount,
                  Integer complexTypeTest) {
        this.name = name;
        this.age = age;
        this.taxRate = taxRate;
        this.bankAccount = bankAccount;
        this.complexTypeTest = complexTypeTest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(float taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BigDecimal bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Integer getComplexTypeTest() {
        return complexTypeTest;
    }

    public void setComplexTypeTest(Integer complexTypeTest) {
        this.complexTypeTest = complexTypeTest;
    }
}
