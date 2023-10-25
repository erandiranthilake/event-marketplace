package com.uta.adveng.eventmarketplace.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @NonNull
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
}
