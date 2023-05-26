package com.aledluca.ironExpress;

import com.aledluca.ironExpress.models.Seller;
import com.aledluca.ironExpress.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.time.LocalDateTime;

@SpringBootApplication
public class IronExpressApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(IronExpressApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("basicScheme",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
				.info(new Info().title("IronExpress Application REST API").version(appVersion)
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}


	@Autowired
	private SellerRepository sellerRepository;

	@Override
	public void run(String... args) throws Exception {
		sellerRepository.save(new Seller("Lucas", "Basilio",  "6123456789", "aledluca@gmail.com", "12345678"));
	}
}
