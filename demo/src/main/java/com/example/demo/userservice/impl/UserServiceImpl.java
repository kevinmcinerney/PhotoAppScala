package com.example.demo.userservice.impl;

import com.example.demo.model.request.UserDetailsRequest;
import com.example.demo.model.response.UserRest;
import com.example.demo.shared.Utils;
import com.example.demo.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    Map<String, UserRest> users;
    Utils utils;

    public UserServiceImpl(){};

    @Autowired // constructor based dependency injection
    public UserServiceImpl(Utils utils){
        this.utils = utils;
    }


    @Override
    public UserRest createUser(UserDetailsRequest userDetails) {

        UserRest returnValue = new UserRest();
        returnValue.setEmail(userDetails.getEmail());
        returnValue.setFirstName(userDetails.getFirstName());
        returnValue.setLastName(userDetails.getLastName());

        String userId = utils.generateUserId();
        returnValue.setUserId(userId);

        if (users == null) users = new HashMap<>();
        users.put(userId, returnValue);

        return returnValue;
    }
}
