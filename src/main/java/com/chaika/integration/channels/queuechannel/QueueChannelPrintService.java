package com.chaika.integration.channels.queuechannel;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * Created by echaika on 27.03.2019
 */
public class QueueChannelPrintService {

    public Message<String> print(Message<String> message) {
        System.out.println(message.getPayload());
        int messageNumber = (int) message.getHeaders().get("messageNumber");
        return MessageBuilder.withPayload("Sending a reply for message " + messageNumber).build();
    }
}
