package com.uta.adveng.eventmarketplace.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @GetMapping("/users/getUsers")
    public String getUsers() {
        return "Erandi and Sharoon";
    }
}
