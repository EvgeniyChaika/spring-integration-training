package com.chaika.integration.message;

import org.springframework.messaging.Message;

/**
 * Created by echaika on 25.03.2019
 */
public class MessagePrintService {

    public void print(Message<?> message) {
        System.out.println("--Message headers--");
        message.getHeaders().forEach((key, value) -> System.out.println(key + ": " + value));
        System.out.println("--Message payload--");
        System.out.println(message.getPayload() + "\n");
    }
}
