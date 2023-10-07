package com.uta.adveng.eventmarketplace.service;

import com.uta.adveng.eventmarketplace.dao.Company;
import com.uta.adveng.eventmarketplace.dao.Users;
import com.uta.adveng.eventmarketplace.dataaccess.ICompanyRepository;
import com.uta.adveng.eventmarketplace.dataaccess.IUsersRepository;
import com.uta.adveng.eventmarketplace.dto.RegistrationForm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    IUsersRepository usersRepository;
    ICompanyRepository companyRepository;

    public Users saveRegistrationForm(RegistrationForm registrationForm) {
        if(checkIfUserNameExist(registrationForm.getUsername())) {
            throw new RuntimeException("USER ALREADY EXIST");
        }
        Users user =  saveUserRecord(registrationForm);

        if(registrationForm.getRole().equals("VENDOR")) {
            saveCompanyRecord(registrationForm);
        }
        return user;
    }

    public Users loginUser(String userame, String password) {
        if(!checkIfUserNameExist(userame)) {
            throw new RuntimeException("USER DOES NOT EXIST. PLEASE REGISTER.");
        }
        Users user = usersRepository.findByUsernameAndPassword(userame, password);
        if(user == null) {
            throw new RuntimeException("INVALID USERNAME OR PASSWORD");
        }
        return user;
    }

    private void saveCompanyRecord(RegistrationForm registrationForm) {
        Company company = new Company(registrationForm.getUsername(),
                registrationForm.getCompanyname(),
                registrationForm.getAddresslineone(),
                registrationForm.getAddresslinetwo(),
                registrationForm.getCity(),
                registrationForm.getState(),
                registrationForm.getZipcode());

        companyRepository.save(company);
    }

    private boolean checkIfUserNameExist(String username) {
        Users user = usersRepository.findByUsername(username);
        return user != null;
    }

    private Users saveUserRecord(RegistrationForm registrationForm) {
        Users user = new Users(registrationForm.getUsername(),
                registrationForm.getPassword(),
                registrationForm.getFirstname(),
                registrationForm.getLastname(),
                registrationForm.getEmail(),
                registrationForm.getRole());

        return usersRepository.save(user);
    }
}
