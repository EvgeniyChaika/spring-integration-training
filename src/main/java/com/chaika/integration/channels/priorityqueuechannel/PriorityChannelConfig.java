package com.chaika.integration.channels.priorityqueuechannel;

import com.chaika.integration.channels.queuechannel.QueueChannelPrintService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PriorityChannel;
import org.springframework.messaging.Message;

/**
 * Created by echaika on 01.04.2019
 */
@Configuration
@IntegrationComponentScan("com.chaika.integration.channels.priorityqueuechannel")
public class PriorityChannelConfig {

    @Bean
    public PriorityChannel priorityChannel() {
        return new PriorityChannel(10);
    }

    @ServiceActivator(inputChannel = "priorityChannel", poller = @Poller(fixedRate = "5000", maxMessagesPerPoll = "2"))
    public Message<?> priorityQueueChannelServiceActivator(Message<String> message) {
        return new QueueChannelPrintService().print(message);
    }
}
