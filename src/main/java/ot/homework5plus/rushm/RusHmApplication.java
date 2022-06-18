package ot.homework5plus.rushm;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMongock
public class RusHmApplication {

    public static void main(String[] args) {
        SpringApplication.run(RusHmApplication.class, args);
    }

}
