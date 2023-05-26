package com.aledluca.ironExpress;

import com.aledluca.ironExpress.models.Seller;
import com.aledluca.ironExpress.repository.SellerRepository;
import com.aledluca.ironExpress.security.models.Role;
import com.aledluca.ironExpress.security.models.User;
import com.aledluca.ironExpress.security.service.impl.UserService;
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
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;

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

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SELLER"));
			userService.saveRole(new Role(null, "ROLE_CUSTOMER"));

			userService.saveUser(new User(null, "Lucas Basilio", "lucas", "1234", new ArrayList<>(), new ArrayList<>()));
			userService.saveUser(new User(null, "James Smith", "james", "0000", new ArrayList<>(),new ArrayList<>()));
			userService.saveUser(new User(null, "Jane Carry", "jane", "1111", new ArrayList<>(), new ArrayList<>()));

			userService.addRoleToUser("lucas", "ROLE_ADMIN");
			userService.addRoleToUser("lucas", "ROLE_SELLER");
			userService.addRoleToUser("lucas", "ROLE_CUSTOMER");
			userService.addRoleToUser("james", "ROLE_CUSTOMER");
			userService.addRoleToUser("jane", "ROLE_SELLER");
		};
	}

	@Autowired
	private SellerRepository sellerRepository;

	@Override
	public void run(String... args) throws Exception {
		sellerRepository.save(new Seller("Lucas", "Basilio",  "6123456789", "aledluca@gmail.com", "12345678"));
	}
}
