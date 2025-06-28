package com.brold.healthTalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
		"com.brold.healthTalk.user.repository"})
@EntityScan(basePackages = {
		"com.brold.healthTalk.user.entity"})
public class HealthTalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthTalkApplication.class, args);
	}

}
