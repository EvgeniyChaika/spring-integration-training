package com.chaika.integration.message;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by echaika on 25.03.2019
 */
@Configuration
public class MessageConfig {

    @Bean
    public MessagePrintService messagePrintService() {
        return new MessagePrintService();
    }
}
