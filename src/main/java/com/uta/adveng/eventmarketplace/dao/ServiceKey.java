package com.uta.adveng.eventmarketplace.dao;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceKey implements Serializable {
    private String companyId;
    private String serviceId;
}
