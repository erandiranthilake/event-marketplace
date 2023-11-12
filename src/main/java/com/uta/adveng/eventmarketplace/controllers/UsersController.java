package com.uta.adveng.eventmarketplace.controllers;

import com.uta.adveng.eventmarketplace.dao.Service;
import com.uta.adveng.eventmarketplace.dao.ServiceKey;
import com.uta.adveng.eventmarketplace.dao.Users;
import com.uta.adveng.eventmarketplace.dataaccess.IServiceRepository;
import com.uta.adveng.eventmarketplace.dataaccess.IUsersRepository;
import com.uta.adveng.eventmarketplace.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UsersController {

    private IUsersRepository usersRepository;

    @CrossOrigin
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        ResponseEntity responseEntity;
        try{
            List<Users> response = usersRepository.findAll();
            responseEntity = new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (RuntimeException re) {
            responseEntity = new ResponseEntity<Object>(re.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<Object>("Failed to get all Users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @CrossOrigin
    @GetMapping("/users/{username}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        ResponseEntity responseEntity;
        try{
            ValidationUtil.checkNotNull(username, "UserName");
            Users response = usersRepository.findByUsername(username);
            responseEntity = new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (RuntimeException re) {
            responseEntity = new ResponseEntity<Object>(re.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<Object>("Failed to get service by Company Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @CrossOrigin
    @DeleteMapping("/users/{username}")
    @Transactional
    public ResponseEntity<Object> deleteUserByUsername(@PathVariable String username) {
        ResponseEntity responseEntity;
        try{
            ValidationUtil.checkNotNull(username, "UserName");
            usersRepository.deleteById(username);
            responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
        } catch (RuntimeException re) {
            responseEntity = new ResponseEntity<Object>(re.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<Object>("Failed to delete service by Service Key", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
