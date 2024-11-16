package org.youcode.citronix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"org.youcode.citronix"})
public class CitronixApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitronixApplication.class, args);
    }

}
