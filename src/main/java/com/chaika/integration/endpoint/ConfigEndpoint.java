package com.chaika.integration.endpoint;

import com.chaika.integration.message.MessagePrintService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;

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
    public void subscriberEndpointOutputChannel() {
        endpointOutputChannel().subscribe(message -> new MessagePrintService().print(message));
    }

    @ServiceActivator(inputChannel = "endpointInputChannel", outputChannel = "endpointOutputChannel")
    public Message<?> endpointServiceActivator(Message<?> message) {
        return endpointMessagePrintService().print(message);
    }
}
