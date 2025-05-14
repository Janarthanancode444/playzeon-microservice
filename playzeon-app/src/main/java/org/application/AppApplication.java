package org.application;

import org.application.dto.QRPropertiesDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"org.user", "org.application", "org.application.controller", "org.application.service", "org.application.util"})
@EntityScan(basePackages = {"org.app.entity"})
@EnableJpaRepositories(basePackages = {"org.user.repository", "org.application.repository"})
@EnableConfigurationProperties(QRPropertiesDTO.class)
public class AppApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
        Resource image = new ClassPathResource("F:\\Internship\\Microservice-playzeon\\playzeon-app\\src\\main\\resources\\images\\spm_23e1.png");

    }
}
