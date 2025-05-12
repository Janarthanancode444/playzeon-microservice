package org.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"org.user", "org.application", "org.application.controller", "org.application.service", "org.application.util"})
@EntityScan(basePackages = {"org.app.entity"})
@EnableJpaRepositories(basePackages = {"org.user.repository", "org.application.repository"})

public class AppApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
