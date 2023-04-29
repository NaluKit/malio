package com.github.nalukit.malio.test.model.maxvalue01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.MaxValue;

@MalioValidator
public class Person {

    private String name;

    @MaxValue(99)
    private int age;

    @MaxValue(99)
    private long numberChildren;

    @MaxValue(123)
    private Integer complexTypeTest;

    public Person() {
    }

    public Person(String name,
                  int age,
                  long numberChildren,
                  Integer complexTypeTest) {
        this.name = name;
        this.age = age;
        this.numberChildren = numberChildren;
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
