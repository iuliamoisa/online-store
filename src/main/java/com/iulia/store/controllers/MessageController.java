package com.iulia.store.controllers;

import com.iulia.store.entities.Message;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private Message message;
    public MessageController() {
        this.message = new Message("Welcome to Iulia's Store!");
    }
}
