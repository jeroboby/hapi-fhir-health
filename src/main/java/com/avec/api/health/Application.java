package com.avec.api.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	public ServletRegistrationBean ServletRegistrationBean() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new FhirRestfulServer(), "/*");
		registration.setName("FhirServlet");
		return registration;

	}

}
