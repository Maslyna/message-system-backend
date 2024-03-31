package net.maslyna.security.test;

import lombok.extern.slf4j.Slf4j;
import net.maslyna.security.SecurityApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;


@Slf4j
@TestConfiguration(proxyBeanMethods = false)
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.from(SecurityApplication::main)
                .with(TestApplication.class)
                .run(args);
    }
}
