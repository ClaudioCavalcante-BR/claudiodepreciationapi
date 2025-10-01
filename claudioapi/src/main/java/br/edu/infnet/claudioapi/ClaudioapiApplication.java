package br.edu.infnet.claudioapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "br.edu.infnet.claudioapi.model.clients")
@SpringBootApplication
public class ClaudioapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClaudioapiApplication.class, args);
	}
}
