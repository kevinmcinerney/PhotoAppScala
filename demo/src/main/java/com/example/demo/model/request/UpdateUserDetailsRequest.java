package com.example.demo.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateUserDetailsRequest {

    @NotNull(message = "firstName cannot be null")
    @Size(min = 2, message = "firstName must be greater than or equal to 2 chars")
    private String firstName;

    @NotNull(message = "lastName cannot be null")
    private String lastName;

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
}