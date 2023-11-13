package com.uta.adveng.eventmarketplace.controllers;

import com.uta.adveng.eventmarketplace.dao.Location;
import com.uta.adveng.eventmarketplace.dao.Users;
import com.uta.adveng.eventmarketplace.dataaccess.ILocationRepository;
import com.uta.adveng.eventmarketplace.dataaccess.IUsersRepository;
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

@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IUsersRepository usersRepository;

    private final Users users = new Users("username", "password", "firstname", "lastname", "email", "CUSTOMER");

    @Test
    public void getAllUsers_return_success() throws Exception {
        Mockito.when(usersRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(users)));
        mvc.perform(MockMvcRequestBuilders
                .get("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(new ArrayList<>(Arrays.asList(users))))));
    }

    @Test
    public void getUserByUsername_return_success() throws Exception {
        Mockito.when(usersRepository.findByUsername("username")).thenReturn(users);
        mvc.perform(MockMvcRequestBuilders
                .get("/api/users/{username}", "username")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(users))));
    }

    @Test
    public void deleteUserByUsername_return_success() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/users/{username}","username")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(usersRepository, times(1)).deleteById("username");
    }

    @Test
    public void getAllUsers_throw_exception() throws Exception {
        Mockito.when(usersRepository.findAll()).thenThrow(new RuntimeException());
        mvc.perform(MockMvcRequestBuilders
                .get("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getUserByUsername_throw_exception() throws Exception {
        Mockito.when(usersRepository.findByUsername("username")).thenThrow(new RuntimeException());
        mvc.perform(MockMvcRequestBuilders
                .get("/api/users/{username}", "username")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

}
