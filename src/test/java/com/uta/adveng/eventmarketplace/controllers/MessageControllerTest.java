package com.uta.adveng.eventmarketplace.controllers;

import com.uta.adveng.eventmarketplace.dao.Message;
import com.uta.adveng.eventmarketplace.dao.Users;
import com.uta.adveng.eventmarketplace.dataaccess.IMessageRepository;
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

@WebMvcTest(MessageController.class)
public class MessageControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private IMessageRepository messageRepository;

    private final Message message = new Message(1, "senderId", "receiverId", "messageHeader", "message", "status");

    @Test
    public void postMessage_return_success() throws Exception {
        Mockito.when(messageRepository.save(message)).thenReturn(message);
        mvc.perform(MockMvcRequestBuilders
                .post("/api/message/create")
                .content(asJsonString(message))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getMessageBySenderId_return_success() throws Exception {
        Mockito.when(messageRepository.findBySenderId("senderId")).thenReturn(new ArrayList<>(Arrays.asList(message)));
        mvc.perform(MockMvcRequestBuilders.get("/api/message/send/{senderId}","senderId")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(message))));
    }

    @Test
    public void getMessageBySReceiverId_return_success() throws Exception {
        Mockito.when(messageRepository.findByReceiverId("receiverId")).thenReturn(new ArrayList<>(Arrays.asList(message)));
        mvc.perform(MockMvcRequestBuilders.get("/api/message/receive/{receiverId}","receiverId")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(message))));
    }
}
