package com.chaika.integration;

import com.chaika.integration.demo.DemoCustomGateway;
import com.chaika.integration.message.MessagePrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Configuration
@ImportResource("demo-integration-context.xml")
public class SpringIntegrationTrainingApplication implements ApplicationRunner {

    private DemoCustomGateway gateway;

    private MessagePrintService messagePrintService;

    private DirectChannel directChannel;

    private DirectChannel endpointInputChannel;

    @Autowired
    public SpringIntegrationTrainingApplication(DemoCustomGateway gateway, MessagePrintService messagePrintService,
                                                @Qualifier("channelDirectChannel") DirectChannel directChannel,
                                                @Qualifier("endpointInputChannel") @Lazy DirectChannel endpointInputChannel) {
        this.gateway = gateway;
        this.messagePrintService = messagePrintService;
        this.directChannel = directChannel;
        this.endpointInputChannel = endpointInputChannel;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationTrainingApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //demo
        System.out.println("-------------demo-------------");
        gateway.print("Demo test");

        //message
        System.out.println("-------------message-------------");
        Map<String, Object> map = new HashMap<>();
        map.put("key", "value");
        map.put("newHeaderName", "newHeaderValue");

        Message<String> message = MessageBuilder
                .withPayload("Test message")
                .copyHeadersIfAbsent(map)
                .build();
        messagePrintService.print(message);

        //channel
        System.out.println("-------------channel-------------");
        directChannel.send(message);

        //endpoint
        System.out.println("-------------endpoint-------------");
        endpointInputChannel.send(message);
    }
}
