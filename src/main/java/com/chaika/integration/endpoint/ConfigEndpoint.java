package com.chaika.integration.endpoint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageHandler;

/**
 * Created by echaika on 25.03.2019
 */
@Configuration
public class ConfigEndpoint {

    @Bean
    public DirectChannel endpointInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel endpointOutputChannel() {
        return new DirectChannel();
    }

    @Bean
    public EndpointMessagePrintService endpointMessagePrintService() {
        return new EndpointMessagePrintService();
    }

    @Bean
    void subscriberEndpointOutputChannel() {
        endpointOutputChannel().subscribe(message -> System.out.println(message.getPayload()));
    }

    //TODO fix inputChannel creation bug
    @Bean
    @ServiceActivator(inputChannel = "endpointInputChannel", outputChannel = "endpointOutputChannel")
    public MessageHandler endpointServiceActivator() {
        return message -> endpointMessagePrintService().print(message);
    }
}
