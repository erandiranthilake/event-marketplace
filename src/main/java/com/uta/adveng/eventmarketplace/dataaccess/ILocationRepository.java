package com.uta.adveng.eventmarketplace.dataaccess;

import com.uta.adveng.eventmarketplace.dao.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocationRepository extends JpaRepository<Location, String> {
}
