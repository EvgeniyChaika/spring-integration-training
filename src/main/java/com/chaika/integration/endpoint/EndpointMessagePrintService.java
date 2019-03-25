package com.chaika.integration.endpoint;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * Created by echaika on 25.03.2019
 */
public class EndpointMessagePrintService {

    public Message<?> print(Message<?> message) {

        System.out.println("--Message headers--");
        message.getHeaders().forEach((key, value) -> System.out.println(key + ": " + value));
        System.out.println("--Message payload--");
        System.out.println(message.getPayload());

        return MessageBuilder
                .fromMessage(message)
                .setHeader("newKeyInputChannel", "newValueOutputChannel")
                .build();
    }
}
