package com.chaika.integration.channels.queuechannel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;

/**
 * Created by echaika on 27.03.2019
 */
@Configuration
@IntegrationComponentScan("com.chaika.integration.channels.queuechannel")
public class QueueChannelConfig {

    @Bean
    public QueueChannel queueChannel() {
        return new QueueChannel(10);
    }

    @Bean
    public QueueChannelPrintService queueChannelPrintService() {
        return new QueueChannelPrintService();
    }

    @ServiceActivator(inputChannel = "queueChannel", poller = @Poller(fixedRate = "5000", maxMessagesPerPoll = "2"))
    public Message<?> queueChannelServiceActivator(Message<String> message) {
        return queueChannelPrintService().print(message);
    }
}
