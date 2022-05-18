package com.epam.training.sportsbetting.domain;

import java.util.Objects;

import lombok.experimental.SuperBuilder;



@SuperBuilder
public class User {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + email.hashCode();
        hash = 31 * hash + password.hashCode();
        return hash;
    }
}
