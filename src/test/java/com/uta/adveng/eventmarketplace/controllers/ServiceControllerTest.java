package com.uta.adveng.eventmarketplace.controllers;

import com.uta.adveng.eventmarketplace.dao.Location;
import com.uta.adveng.eventmarketplace.dao.Service;
import com.uta.adveng.eventmarketplace.dao.ServiceKey;
import com.uta.adveng.eventmarketplace.dataaccess.ILocationRepository;
import com.uta.adveng.eventmarketplace.dataaccess.IServiceRepository;
import com.uta.adveng.eventmarketplace.util.ValidationUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static com.uta.adveng.eventmarketplace.util.JsonUtil.asJsonString;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServiceController.class)
public class ServiceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IServiceRepository serviceRepository;
    private final ServiceKey key= new ServiceKey("companyId", "serviceId");
    private final Service service = new Service(key, "serviceName", "description", 100.0);

    @Test
    public void postservice_return_success() throws Exception {
        Mockito.when(serviceRepository.save(service)).thenReturn(service);
        mvc.perform(MockMvcRequestBuilders
                .post("/api/service/create")
                .content(asJsonString(service))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getServiceByServiceKey_return_success() throws Exception {
        Mockito.when(serviceRepository.findByServiceKey(key)).thenReturn(service);
        mvc.perform(MockMvcRequestBuilders
                .get("/api/service/{companyId}/{serviceId}","companyId", "serviceId")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(service))));
    }

    @Test
    public void getAllServicesByCompanyId_return_success() throws Exception {
        Mockito.when(serviceRepository.findByServiceKeyCompanyId("companyId")).thenReturn(new ArrayList<>(Arrays.asList(service)));
        mvc.perform(MockMvcRequestBuilders
                .get("/api/service/{companyId}","companyId")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(new ArrayList<>(Arrays.asList(service))))));
    }

    @Test
    public void deleteServiceByServiceKey_return_success() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/service/{companyId}/{serviceId}","companyId", "serviceId")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postservice_throw_exception() throws Exception {
        Mockito.when(serviceRepository.save(service)).thenThrow(new RuntimeException());
        mvc.perform(MockMvcRequestBuilders
                .post("/api/service/create")
                .content(asJsonString(service))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }


    @Test
    public void getServiceByServiceKey_throw_exception() throws Exception {
        Mockito.when(serviceRepository.findByServiceKey(key)).thenThrow(new RuntimeException());
        mvc.perform(MockMvcRequestBuilders
                .get("/api/service/{companyId}/{serviceId}","companyId", "serviceId")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getAllServicesByCompanyId_throw_exception() throws Exception {
        Mockito.when(serviceRepository.findByServiceKeyCompanyId("companyId")).thenThrow(new RuntimeException());
        mvc.perform(MockMvcRequestBuilders
                .get("/api/service/{companyId}","companyId")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
