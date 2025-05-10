package org.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication (scanBasePackages = {"org.user", "org.app"})
@EntityScan(basePackages = {"org.app.entity"})
@EnableJpaRepositories(basePackages = {"org.user.repository"})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
