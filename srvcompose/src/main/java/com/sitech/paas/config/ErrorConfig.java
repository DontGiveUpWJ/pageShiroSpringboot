package com.sitech.paas.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory>{

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		
		ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
		ErrorPage error505Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/505");
		ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
		factory.addErrorPages(error404Page, error505Page, error500Page);
		
	}
	
}
