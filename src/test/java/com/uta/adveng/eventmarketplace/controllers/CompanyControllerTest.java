package com.uta.adveng.eventmarketplace.controllers;

import com.uta.adveng.eventmarketplace.dataaccess.ICompanyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ICompanyRepository companyRepository;

    @Test
    public void getCompanies_return_success() throws Exception {
        Mockito.when(companyRepository.findAll()).thenReturn(new ArrayList<>());
        mvc.perform(MockMvcRequestBuilders.get("/api/company/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getCompanies_throw_exception() throws Exception {
        Mockito.when(companyRepository.findAll()).thenThrow(new RuntimeException());
        mvc.perform(MockMvcRequestBuilders.get("/api/company/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
