package ot.homework5plus.rushm;

import io.changock.runner.spring.v5.config.EnableChangock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableChangock
@EnableMongoRepositories(basePackages = {"ot.homework5plus.rushm.changelogs"})
@EnableReactiveMongoRepositories(basePackages = {"ot.homework5plus.rushm.repository"})
public class RusHmApplication {

    public static void main(String[] args) {
        SpringApplication.run(RusHmApplication.class, args);
    }

}
