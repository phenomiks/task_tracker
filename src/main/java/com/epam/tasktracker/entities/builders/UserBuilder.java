package com.epam.tasktracker.entities.builders;

import com.epam.tasktracker.entities.User;

public class UserBuilder {
    private String firstname;
    private String lastname;
    private String phone;

    public UserBuilder() {
    }

    public UserBuilder setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public UserBuilder setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public User build() {
        return new User(firstname, lastname, phone);
    }
}
