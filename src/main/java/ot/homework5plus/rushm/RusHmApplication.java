package ot.homework5plus.rushm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
@EnableScheduling
public class RusHmApplication {

    public static void main(String[] args) {
        SpringApplication.run(RusHmApplication.class, args);
    }

}
