package com.chaika.integration.channel;

import com.chaika.integration.message.MessagePrintService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;

/**
 * Created by echaika on 25.03.2019
 */
@Configuration
public class ChannelConfig {

    @Bean
    public DirectChannel channelDirectChannel() {
        return new DirectChannel();
    }

    @Bean
    void subscriberChannelDirectChannel() {
        channelDirectChannel().subscribe(message -> new MessagePrintService().print((Message<String>) message));
    }
}
