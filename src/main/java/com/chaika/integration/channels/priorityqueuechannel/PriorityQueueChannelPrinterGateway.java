package com.chaika.integration.channels.priorityqueuechannel;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

import java.util.concurrent.Future;

/**
 * Created by echaika on 27.03.2019
 */
@MessagingGateway(defaultRequestChannel = "priorityChannel")
public interface PriorityQueueChannelPrinterGateway {

    @Gateway
    Future<Message<String>> print(Message<?> message);
}
