package com.github.nalukit.malio.test.model.minvalue01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.MinValue;

@MalioValidator
public class Person {

    private String name;

    @MinValue(18)
    private int age;

    @MinValue(5)
    private Integer complexTypeTest;

    public Person() {
    }

    public Person(String name,
                  int age,
                  Integer complexTypeTest) {
        this.name = name;
        this.age = age;
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

    public Integer getComplexTypeTest() {
        return complexTypeTest;
    }

    public void setComplexTypeTest(Integer complexTypeTest) {
        this.complexTypeTest = complexTypeTest;
    }
}
