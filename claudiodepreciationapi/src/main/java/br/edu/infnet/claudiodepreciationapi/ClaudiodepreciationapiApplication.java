package br.edu.infnet.claudiodepreciationapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class ClaudiodepreciationapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClaudiodepreciationapiApplication.class, args);
	}

}
