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
public class Company {
    @Id
    @NonNull
    private String companyId;
    private String companyName;
    private String addressLineOne;
    private String addressLineTwo;
    private String city;
    private String state;
    private Integer zipcode;
}
