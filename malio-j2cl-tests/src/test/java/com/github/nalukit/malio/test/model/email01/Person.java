package com.github.nalukit.malio.test.model.email01;

import com.github.nalukit.malio.shared.annotation.MalioValidator;
import com.github.nalukit.malio.shared.annotation.field.Email;

@MalioValidator
public class Person {

    @Email
    private String email;


    public Person() {
    }

    public Person(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
