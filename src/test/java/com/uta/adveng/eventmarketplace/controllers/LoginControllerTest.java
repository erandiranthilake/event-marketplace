package com.uta.adveng.eventmarketplace.controllers;

import com.uta.adveng.eventmarketplace.dao.Users;
import com.uta.adveng.eventmarketplace.dto.RegistrationForm;
import com.uta.adveng.eventmarketplace.service.LoginService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.uta.adveng.eventmarketplace.util.JsonUtil.asJsonString;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LoginService loginService;

    private final RegistrationForm registrationForm = new RegistrationForm("username", "password", "firstname", "lastname", "email", "VENDOR", "companyname", "addresslineone", "addresslinetwo", "city", "state", 77777);
    private final Users users = new Users("username", "password", "firstname", "lastname", "email", "VENDOR");

    @Test
    public void postRegistration_return_success() throws Exception {
        Mockito.when(loginService.saveRegistrationForm(registrationForm)).thenReturn(users);
        ResultActions entity = mvc.perform(MockMvcRequestBuilders
                .post("/api/registration")
                .content(asJsonString(registrationForm))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getLogin_return_success() throws Exception {
        Mockito.when(loginService.loginUser("username", "password")).thenReturn(users);
         mvc.perform(MockMvcRequestBuilders.get("/api/login/{username}/{password}","username", "password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(users))));
    }
}
