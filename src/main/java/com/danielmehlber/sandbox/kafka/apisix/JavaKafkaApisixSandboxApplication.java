package com.danielmehlber.sandbox.kafka.apisix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.danielmehlber.sandbox.kafka.apisix")
public class JavaKafkaApisixSandboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaKafkaApisixSandboxApplication.class, args);
	}

}
