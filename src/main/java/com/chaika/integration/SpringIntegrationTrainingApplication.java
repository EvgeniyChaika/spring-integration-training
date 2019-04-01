package com.chaika.integration;

import com.chaika.integration.channels.priorityqueuechannel.PriorityQueueChannelPrinterGateway;
import com.chaika.integration.channels.queuechannel.QueueChannelPrinterGateway;
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
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@SpringBootApplication
@Configuration
@ImportResource("demo-integration-context.xml")
public class SpringIntegrationTrainingApplication implements ApplicationRunner {

    private DemoCustomGateway gateway;

    private MessagePrintService messagePrintService;

    private DirectChannel directChannel;

    private DirectChannel endpointInputChannel;

    private DirectChannel messagingTemplateInputChannel;

    private QueueChannelPrinterGateway queueChannelPrinterGateway;

    private PriorityQueueChannelPrinterGateway priorityQueueChannelPrinterGateway;

    @Autowired
    public SpringIntegrationTrainingApplication(DemoCustomGateway gateway, MessagePrintService messagePrintService,
                                                @Qualifier("channelDirectChannel") DirectChannel directChannel,
                                                @Qualifier("endpointInputChannel") @Lazy DirectChannel endpointInputChannel,
                                                @Qualifier("messagingTemplateInputChannel") @Lazy DirectChannel messagingTemplateInputChannel,
                                                QueueChannelPrinterGateway queueChannelPrinterGateway,
                                                PriorityQueueChannelPrinterGateway priorityQueueChannelPrinterGateway) {
        this.gateway = gateway;
        this.messagePrintService = messagePrintService;
        this.directChannel = directChannel;
        this.endpointInputChannel = endpointInputChannel;
        this.messagingTemplateInputChannel = messagingTemplateInputChannel;
        this.queueChannelPrinterGateway = queueChannelPrinterGateway;
        this.priorityQueueChannelPrinterGateway = priorityQueueChannelPrinterGateway;
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

        //messaging template
        System.out.println("-------------messaging template-------------");
        MessagingTemplate messagingTemplate = new MessagingTemplate();
        Message receivedMessage = messagingTemplate.sendAndReceive(messagingTemplateInputChannel, message);
        messagePrintService.print(receivedMessage);

        //queue channel
        System.out.println("-------------queue channel-------------");
        List<Future<Message<String>>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message<String> queueChannelMessage = MessageBuilder.withPayload("Printing message payload for " + i)
                    .setHeader("messageNumber", i).build();
            System.out.println("Sending message " + i);
            futures.add(queueChannelPrinterGateway.print(queueChannelMessage));
        }

        for (Future<Message<String>> future : futures) {
            System.out.println(future.get().getPayload());
        }

        //priority queue channel
        System.out.println("-------------priority queue channel-------------");
        List<Future<Message<String>>> futuresPriority = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message<String> queueChannelMessage = MessageBuilder.withPayload("Printing message payload for " + i)
                    .setHeader("messageNumber", i).setPriority(i).build();
            System.out.println("Sending message " + i);
            futuresPriority.add(priorityQueueChannelPrinterGateway.print(queueChannelMessage));
        }

        for (Future<Message<String>> future : futuresPriority) {
            System.out.println(future.get().getPayload());
        }
    }
}
