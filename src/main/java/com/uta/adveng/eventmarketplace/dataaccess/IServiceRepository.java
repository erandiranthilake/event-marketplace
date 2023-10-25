package com.uta.adveng.eventmarketplace.dataaccess;

import com.uta.adveng.eventmarketplace.dao.Service;
import com.uta.adveng.eventmarketplace.dao.ServiceKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceRepository extends JpaRepository<Service, String> {
    public Service findByServiceKey(ServiceKey key);
}
