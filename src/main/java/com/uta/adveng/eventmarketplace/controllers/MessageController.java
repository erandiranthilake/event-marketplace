package com.uta.adveng.eventmarketplace.controllers;

import com.uta.adveng.eventmarketplace.dao.Message;
import com.uta.adveng.eventmarketplace.dataaccess.IMessageRepository;
import com.uta.adveng.eventmarketplace.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MessageController {

    private IMessageRepository messageRepository;

    @PostMapping("/message/create")
    public ResponseEntity<Object> createMessage(@RequestBody Message message) {
        ResponseEntity responseEntity;
        try{
            ValidationUtil.checkNotNull(message.getSenderId(),  "SenderId");
            ValidationUtil.checkNotNull(message.getReceiverId(), "ReceiverId");

            Message response = messageRepository.save(message);
            responseEntity = new ResponseEntity<Object>(response, HttpStatus.CREATED);
        } catch (RuntimeException re) {
            responseEntity = new ResponseEntity<Object>(re.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<Object>("Create Message Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/message/send/{senderId}")
    public ResponseEntity<Object> getMessageBySenderId(@PathVariable String senderId) {
        ResponseEntity responseEntity;
        try{
            ValidationUtil.checkNotNull(senderId, "senderId");

            List<Message> response = messageRepository.findBySenderId(senderId);
            responseEntity = new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (RuntimeException re) {
            responseEntity = new ResponseEntity<Object>(re.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<Object>("Failed to get Message by Sender Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/message/receive/{receiverId}")
    public ResponseEntity<Object> getMessageByReceiverId(@PathVariable String receiverId) {
        ResponseEntity responseEntity;
        try{
            ValidationUtil.checkNotNull(receiverId, "receiverId");

            List<Message> response = messageRepository.findByReceiverId(receiverId);
            responseEntity = new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (RuntimeException re) {
            responseEntity = new ResponseEntity<Object>(re.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<Object>("Failed to get Message by Receiver Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
