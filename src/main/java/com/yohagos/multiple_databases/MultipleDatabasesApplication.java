package com.yohagos.multiple_databases;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableKafka
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
public class MultipleDatabasesApplication {

	public static void main(String[] args) {
		//loadSecrets();
		SpringApplication.run(MultipleDatabasesApplication.class, args);
	}

	private static void loadSecrets() {

	}
}
