package com.example.demo.controllers;

import com.example.demo.exceptions.UserServiceException;
import com.example.demo.model.request.UpdateUserDetailsRequest;
import com.example.demo.model.request.UserDetailsRequest;
import com.example.demo.model.response.UserRest;
import com.example.demo.userservice.UserService;
import com.example.demo.userservice.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/users")
public class UserController {

    Map<String, UserRest> users;
    @Autowired
    UserService userService;

    @GetMapping(path="/{userId}", produces = { MediaType.APPLICATION_XML_VALUE,
                                               MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity getUser(@PathVariable String userId) {

 /*       String firstName = null;

        int firstNameLength = firstName.length();*/

        if(true) throw new UserServiceException("A userservice exception is thrown");

        if(users.containsKey(userId)){
            return new ResponseEntity<UserRest>(users.get(userId), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping
    public String getUsers(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                           @RequestParam(value = "limit", defaultValue = "50") int limit,
                           @RequestParam(value = "sort", required = false) String sort) {
        return "get was called with page = " + page + " and limit = " + limit + " and sort = " + sort;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE,
                              MediaType.APPLICATION_JSON_VALUE },
                 produces = { MediaType.APPLICATION_XML_VALUE,
                              MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequest userDetails) {

        //UserRest returnValue = new UserServiceImpl().createUser(userDetails); // not autowired
        UserRest returnValue =  userService.createUser(userDetails); //autowired

        return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
    }

    @PutMapping(consumes = { MediaType.APPLICATION_XML_VALUE,
                             MediaType.APPLICATION_JSON_VALUE },
                produces = { MediaType.APPLICATION_XML_VALUE,
                             MediaType.APPLICATION_JSON_VALUE },
                path="/{userId}")
    public UserRest updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequest userDetails) {

       UserRest storedUserDetails = users.get(userId);

       storedUserDetails.setFirstName(userDetails.getFirstName());
       storedUserDetails.setLastName((userDetails.getLastName()));

       users.put(userId, storedUserDetails);

       return storedUserDetails;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        users.remove(id);
        return ResponseEntity.noContent().build();
    }
}