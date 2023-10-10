package com.uta.adveng.eventmarketplace.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uta.adveng.eventmarketplace.dao.Company;
import com.uta.adveng.eventmarketplace.dao.Users;
import com.uta.adveng.eventmarketplace.dataaccess.ICompanyRepository;
import com.uta.adveng.eventmarketplace.dto.RegistrationForm;
import com.uta.adveng.eventmarketplace.service.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ICompanyRepository companyRepository;

    private RegistrationForm registrationForm;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void registerCustomer_returnSuccess() throws IOException {
        this.registrationForm = objectMapper.readValue(new ClassPathResource("payloads/customer_registration_form.json").getFile(), RegistrationForm.class);
        Users user = loginService.saveRegistrationForm(registrationForm);
        Assertions.assertEquals("testcustomerusername", user.getUsername());
        Assertions.assertEquals("testcustomerpassword", user.getPassword());
    }

    @Test
    public void registerVendor_returnSuccess() throws IOException {
        this.registrationForm = objectMapper.readValue(new ClassPathResource("payloads/vendor_registration_form.json").getFile(), RegistrationForm.class);
        Users user = loginService.saveRegistrationForm(registrationForm);
        Assertions.assertEquals("testvendorusername", user.getUsername());
        Assertions.assertEquals("testvendorpassword", user.getPassword());
    }

    @Test
    public void registerCompany_returnSuccess() throws IOException {
        this.registrationForm = objectMapper.readValue(new ClassPathResource("payloads/vendor_registration_form.json").getFile(), RegistrationForm.class);
        Users user = loginService.saveRegistrationForm(registrationForm);
        Company company = companyRepository.findByCompanyId(user.getUsername());
        Assertions.assertEquals("TestCompanyname", company.getCompanyName());
    }

    @Test
    public void testRepeatCustomerRegistration_throwsException() throws IOException {
        this.registrationForm = objectMapper.readValue(new ClassPathResource("payloads/vendor_registration_form.json").getFile(), RegistrationForm.class);
        Users user = loginService.saveRegistrationForm(registrationForm);

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            loginService.saveRegistrationForm(registrationForm);
        }, "USER ALREADY EXIST");
    }
}
