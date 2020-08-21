package com.example.demo.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDetailsRequest {

    @NotNull(message="firstName cannot be null")
    @Size(min = 2, message = "firstName must be greater than or equal to 2 chars")
    private String firstName;

    @NotNull(message="lastName cannot be null")
    private String lastName;

    @NotNull(message="email cannot be null")
    @Email
    private String email;

    @NotNull(message="password cannot be null")
    @Size(min=8,max=16, message = "password should be between 8 and 16 chars")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
