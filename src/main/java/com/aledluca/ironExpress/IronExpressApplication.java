package com.aledluca.ironExpress;

import com.aledluca.ironExpress.enums.Category;
import com.aledluca.ironExpress.enums.ProductStatus;
import com.aledluca.ironExpress.models.Customer;
import com.aledluca.ironExpress.models.Product;
import com.aledluca.ironExpress.models.Seller;
import com.aledluca.ironExpress.repository.CustomerRepository;
import com.aledluca.ironExpress.repository.ProductRepository;
import com.aledluca.ironExpress.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.security.SecurityScheme;


@SpringBootApplication
public class IronExpressApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(IronExpressApplication.class, args);
	}

//	@Bean
//	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
//		return new OpenAPI()
//				.components(new Components().addSecuritySchemes("basicScheme",
//						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
//				.info(new Info().title("IronExpress Application REST API").version(appVersion)
//						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
//	}


	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ProductRepository productRepository;
	@Override
	public void run(String... args) throws Exception {
		// Creating sellers
		sellerRepository.save(new Seller("Lucas", "Basilio",  "6111111111", "aledluca@gmail.com", "@senhae01"));
		sellerRepository.save(new Seller("Daniel", "Monteiro",  "6222222222", "montdani@gmail.com", "@senhae02"));
		// Creating customers
		customerRepository.save(new Customer("Laura", "Gimenez",  "6333333333", "gimlau@gmail.com", "@senhae03"));
		customerRepository.save(new Customer("Roberto", "Naval",  "6444444444", "navro@gmail.com", "@senhae04"));
		// Creating products
		productRepository.save(new Product("Xanex", 14.99, "Jabon intimo", "Asturias Productos Ltd", 100, Category.GROCERIES, ProductStatus.AVAILABLE));
		productRepository.save(new Product("Muñeca Turma da Monica", 119.99, "Muñeca para crios", "Laerte Minion Ltd", 0, Category.FASHION, ProductStatus.OUTOFSTOCK));
		productRepository.save(new Product("70-1 Rules for Life", 49.99, "Self-help book", "Penguin Books", 0, Category.BOOKS, ProductStatus.AVAILABLE));

	}
}
