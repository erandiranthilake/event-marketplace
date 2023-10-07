package com.uta.adveng.eventmarketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationForm {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
    private String companyname;
    private String addresslineone;
    private String addresslinetwo;
    private String city;
    private String state;
    private Integer zipcode;
}
