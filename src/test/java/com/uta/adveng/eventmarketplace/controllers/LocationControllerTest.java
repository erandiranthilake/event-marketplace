package com.uta.adveng.eventmarketplace.controllers;

import com.uta.adveng.eventmarketplace.dao.Location;
import com.uta.adveng.eventmarketplace.dao.Users;
import com.uta.adveng.eventmarketplace.dataaccess.ILocationRepository;
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

import java.util.ArrayList;
import java.util.Arrays;

import static com.uta.adveng.eventmarketplace.util.JsonUtil.asJsonString;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ILocationRepository locationRepository;

    private final Location location = new Location(1, "companyId", "serviceId", "addressLineOne", "addressLineTwo", "city", "state", 77777);

    @Test
    public void postLocation_return_success() throws Exception {
        Mockito.when(locationRepository.save(location)).thenReturn(location);
        ResultActions entity = mvc.perform(MockMvcRequestBuilders
                .post("/location/create")
                .content(asJsonString(location))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getLocation_return_success() throws Exception {
        Mockito.when(locationRepository.findByCompanyIdAndServiceId("companyId", "serviceId")).thenReturn(new ArrayList<>(Arrays.asList(location)));
        mvc.perform(MockMvcRequestBuilders.get("/api/location/{companyId}/{serviceId}","companyId", "serviceId")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(new ArrayList<>(Arrays.asList(location))))));
    }

}
