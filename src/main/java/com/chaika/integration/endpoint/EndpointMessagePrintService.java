package com.chaika.integration.endpoint;

import com.chaika.integration.message.MessagePrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * Created by echaika on 25.03.2019
 */
public class EndpointMessagePrintService {

    @Autowired
    private MessagePrintService messagePrintService;

    public Message<?> print(Message<?> message) {
        messagePrintService.print(message);

        return MessageBuilder
                .fromMessage(message)
                .setHeader("newKeyInputChannel", "newValueOutputChannel")
                .build();
    }
}
