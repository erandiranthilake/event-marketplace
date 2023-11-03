package com.uta.adveng.eventmarketplace.controllers;

import com.uta.adveng.eventmarketplace.dao.Company;
import com.uta.adveng.eventmarketplace.dataaccess.ICompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CompanyController {

    private ICompanyRepository companyRepository;

    @CrossOrigin
    @GetMapping("/company/all")
    public ResponseEntity<Object> getAllCompanies() {
        ResponseEntity responseEntity;
        try{
            List<Company> response = companyRepository.findAll();
            responseEntity = new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (RuntimeException re) {
            responseEntity = new ResponseEntity<Object>(re.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<Object>("Failed to get All Companies", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
