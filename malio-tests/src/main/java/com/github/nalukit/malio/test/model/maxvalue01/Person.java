package com.github.nalukit.malio.test.model.maxvalue01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.DecimalMaxValue;
import com.github.nalukit.malio.shared.annotation.field.MaxValue;
import com.github.nalukit.malio.shared.annotation.field.NotNull;
import com.github.nalukit.malio.test.model.notnull01.Address;

import java.math.BigDecimal;

@MalioValidator
public class Person {

    private String name;

    @MaxValue(99)
    private int age;

    @MaxValue(99)
    private long numberChildren;

    @MaxValue(123)
    private Integer complexTypeTest;

//    @MaxValue(10)
    private float taxRate;

    @DecimalMaxValue("100000")
    private BigDecimal bankAccount;

    public Person() {
    }

    public Person(String name,
                  int age,
                  float taxRate,
                  long numberChildren,
                  BigDecimal bankAccount,
                  Integer complexTypeTest) {
        this.name = name;
        this.age = age;
        this.taxRate = taxRate;
        this.numberChildren = numberChildren;
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

    public long getNumberChildren() {
        return numberChildren;
    }

    public void setNumberChildren(long numberChildren) {
        this.numberChildren = numberChildren;
    }

    public Integer getComplexTypeTest() {
        return complexTypeTest;
    }

    public void setComplexTypeTest(Integer complexTypeTest) {
        this.complexTypeTest = complexTypeTest;
    }
}
