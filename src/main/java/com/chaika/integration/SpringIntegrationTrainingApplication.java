package com.chaika.integration;

import com.chaika.integration.demo.DemoCustomGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Configuration
@ImportResource("demo-integration-context.xml")
public class SpringIntegrationTrainingApplication implements ApplicationRunner {

    private DemoCustomGateway gateway;

    @Autowired
    public SpringIntegrationTrainingApplication(DemoCustomGateway gateway) {
        this.gateway = gateway;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationTrainingApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //demo
        gateway.print("Demo test");
    }
}
