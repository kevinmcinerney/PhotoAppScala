package com.example.demo.userservice;

import com.example.demo.model.request.UserDetailsRequest;
import com.example.demo.model.response.UserRest;

public interface UserService {
    UserRest createUser(UserDetailsRequest userDetails);
}
