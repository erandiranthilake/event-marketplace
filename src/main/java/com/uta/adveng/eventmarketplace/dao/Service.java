package com.uta.adveng.eventmarketplace.dao;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    @EmbeddedId
    private ServiceKey serviceKey;
    private String serviceName;
    private String description;
    private Double price;
}
