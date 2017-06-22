package uk.sky.commerce.test.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@Slf4j
@SpringBootApplication
@EnableKafka
public class MainApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MainApplication.class, args);
	}

}
