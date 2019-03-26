package com.chaika.integration.messagingtemplate;

import com.chaika.integration.endpoint.EndpointMessagePrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * Created by echaika on 26.03.2019
 */
@Configuration
public class MessagingTemplateConfig {

    private EndpointMessagePrintService endpointMessagePrintService;

    @Autowired
    public MessagingTemplateConfig(EndpointMessagePrintService endpointMessagePrintService) {
        this.endpointMessagePrintService = endpointMessagePrintService;
    }

    @Bean
    public DirectChannel messagingTemplateInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "messagingTemplateInputChannel")
    public Message<?> messagingTemplateServiceActivator(Message<?> message) {
        return MessageBuilder
                .fromMessage(endpointMessagePrintService.print(message))
                .setHeader("newKeyMessagingTemplateServiceActivator", "newValueMessagingTemplateServiceActivator")
                .build();
    }
}
