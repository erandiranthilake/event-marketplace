package com.uta.adveng.eventmarketplace.dataaccess;

import com.uta.adveng.eventmarketplace.dao.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageRepository extends JpaRepository<Message, String> {
}
