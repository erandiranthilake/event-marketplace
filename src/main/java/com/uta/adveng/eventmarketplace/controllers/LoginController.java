package com.uta.adveng.eventmarketplace.controllers;

import com.uta.adveng.eventmarketplace.dao.Users;
import com.uta.adveng.eventmarketplace.dto.RegistrationForm;
import com.uta.adveng.eventmarketplace.service.LoginService;
import com.uta.adveng.eventmarketplace.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;

    @PostMapping("/registration")
    public ResponseEntity<Object> registerUser(@RequestBody RegistrationForm registrationForm) {
        ResponseEntity responseEntity;
        try{
            ValidationUtil.checkNotNull(registrationForm.getUsername(), "UserName");
            ValidationUtil.checkNotNull(registrationForm.getPassword(), "Password");

            Users user = loginService.saveRegistrationForm(registrationForm);
            responseEntity = new ResponseEntity<Object>(user, HttpStatus.CREATED);
        } catch (RuntimeException re) {
            responseEntity = new ResponseEntity<Object>(re.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<Object>("REGISTRATION FAILED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/login/{username}/{password}")
    public ResponseEntity<Object> loginUser(@PathVariable String username, @PathVariable String password) {
        ResponseEntity responseEntity;
        try{
            ValidationUtil.checkNotNull(username, "UserName");
            ValidationUtil.checkNotNull(password, "Password");

            Users user = loginService.loginUser(username, password);
            responseEntity = new ResponseEntity<Object>(user, HttpStatus.OK);
        } catch (RuntimeException re) {
            responseEntity = new ResponseEntity<Object>(re.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<Object>("LOGIN FAILED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
